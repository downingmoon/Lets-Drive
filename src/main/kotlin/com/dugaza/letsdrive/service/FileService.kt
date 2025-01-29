package com.dugaza.letsdrive.service

import com.dugaza.letsdrive.config.FileProperties
import com.dugaza.letsdrive.entity.file.FileDetail
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import com.dugaza.letsdrive.repository.file.FileDetailRepository
import com.dugaza.letsdrive.repository.file.FileMasterRepository
import net.coobird.thumbnailator.Thumbnailator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.UUID
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes

@Service
@Transactional(readOnly = true)
class FileService(
    private val fileDetailRepository: FileDetailRepository,
    private val fileMasterRepository: FileMasterRepository,
    private val userService: UserService,
    private val fileProperties: FileProperties,
) {
    private val maxSize = fileProperties.maxSize
    private val imageExt = fileProperties.imageExtensionSet()
    private val uncompressedExt = fileProperties.uncompressedExtensionSet()
    private val allowedExt = imageExt + fileProperties.allowedExtensionSet()
    private val uploadRoot: Path = Path.of(fileProperties.uploadRoot).createDirectories()

    @Transactional
    fun uploadFile(
        userId: UUID,
        files: List<MultipartFile>,
    ): Pair<FileMaster, List<FileDetail>> {
        val user = userService.getUserById(userId)
        val master = fileMasterRepository.save(FileMaster(user))
        val detailList = files.map { saveFileDetail(master, it) }
        val saved = fileDetailRepository.saveAll(detailList)
        return master to saved.toList()
    }

    private fun saveFileDetail(
        fileMaster: FileMaster,
        file: MultipartFile,
    ): FileDetail {
        val originalName = file.originalFilename ?: "unknown"
        val mimeType = file.contentType ?: "application/octet-stream"
        val fileBytes = file.bytes
        val originalSize = fileBytes.size.toLong()

        val originalExtension = extractExtension(originalName).lowercase()
        if (!allowedExt.contains(originalExtension)) {
            throw BusinessException(ErrorCode.INVALID_EXTENSION)
        }

        val fileHash = computeFileHash(fileBytes)

        val existing = fileDetailRepository.findByFileHash(fileHash)

        if (existing != null) {
            val newFileDetail =
                FileDetail(
                    fileMaster = fileMaster,
                    originalName = originalName,
                    storedName = existing.storedName,
                    storedPath = existing.storedPath,
                    originalSize = existing.originalSize,
                    storedSize = existing.storedSize,
                    originalExtension = existing.originalExtension,
                    storedExtension = existing.storedExtension,
                    mimeType = existing.mimeType,
                    fileHash = existing.fileHash,
                    thumbnailPath = existing.thumbnailPath,
                    compressed = existing.compressed,
                )
            return newFileDetail
        } else {
            var thumbnailPath: String? = null
            if (imageExt.contains(originalExtension)) {
                try {
                    thumbnailPath = createThumbnail(fileBytes, originalExtension)
                } catch (e: Exception) {
                    throw BusinessException(ErrorCode.IMAGE_THUMBNAIL_GENERATION_FAILED)
                }
            }

            var finalBytes = fileBytes
            var compressed = false
            var storedExtension = originalExtension
            if (uncompressedExt.contains(originalExtension) && originalSize > maxSize) {
                try {
                    finalBytes = zipCompress(fileBytes, originalName)
                    compressed = true
                    storedExtension = "zip"
                } catch (e: Exception) {
                    throw BusinessException(ErrorCode.FILE_COMPRESSION_FAILED)
                }
            }

            val storedSize = finalBytes.size.toLong()

            val storedName = "${UUID.randomUUID()}.$storedExtension"
            val storedPath = uploadRoot.resolve(storedName)
            storedPath.writeBytes(finalBytes)

            return FileDetail(
                fileMaster = fileMaster,
                originalName = originalName,
                storedName = storedName,
                storedPath = storedPath.toString(),
                originalSize = originalSize,
                storedSize = storedSize,
                originalExtension = originalExtension,
                storedExtension = storedExtension,
                mimeType = mimeType,
                fileHash = fileHash,
                thumbnailPath = thumbnailPath,
                compressed = compressed,
            )
        }
    }

    fun getFileDetail(detailId: UUID): FileDetail {
        return fileDetailRepository.findById(detailId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND_FILE_DETAIL) }
    }

    fun loadFileContent(detail: FileDetail): ByteArray {
        val path = Path.of(detail.storedPath)
        return Files.readAllBytes(path)
    }

    fun downloadFile(detailId: UUID): ByteArray {
        val detail = getFileDetail(detailId)
        var fileBytes = loadFileContent(detail)

        if (detail.compressed) {
            try {
                fileBytes = unzipSingleFile(fileBytes)
            } catch (e: Exception) {
                throw BusinessException(ErrorCode.FILE_DECOMPRESSION_FAILED)
            }
        }
        return fileBytes
    }

    private fun extractExtension(fileName: String): String {
        val lastDot = fileName.lastIndexOf('.')
        return if (lastDot > 0) fileName.substring(lastDot + 1) else ""
    }

    private fun computeFileHash(bytes: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(bytes).joinToString("") { "%02x".format(it) }
    }

    private fun zipCompress(
        srcBytes: ByteArray,
        entryName: String,
    ): ByteArray {
        val bos = ByteArrayOutputStream()
        ZipOutputStream(bos).use { zos ->
            val entry = ZipEntry(entryName)
            zos.putNextEntry(entry)
            zos.write(srcBytes)
            zos.closeEntry()
        }
        return bos.toByteArray()
    }

    private fun createThumbnail(
        imageBytes: ByteArray,
        extension: String,
    ): String {
        val thumbnailName = "${UUID.randomUUID()}_thumb.$extension"
        val thumbnailPath = uploadRoot.resolve(thumbnailName)

        ByteArrayInputStream(imageBytes).use { input ->
            val checkImage = ImageIO.read(input) ?: throw BusinessException(ErrorCode.INVALID_IMAGE_DATA)
        }

        ByteArrayInputStream(imageBytes).use { input ->
            ByteArrayOutputStream().use { output ->
                Thumbnailator.createThumbnail(input, output, 1280, 720) // todo ui 구성 완료되면 비율조정
                thumbnailPath.writeBytes(output.toByteArray())
            }
        }
        return thumbnailPath.toString()
    }

    private fun unzipSingleFile(zipBytes: ByteArray): ByteArray {
        ByteArrayInputStream(zipBytes).use { bais ->
            ZipInputStream(bais).use { zis ->
                val entry = zis.nextEntry ?: throw BusinessException(ErrorCode.FILE_DECOMPRESSION_FAILED)
                val buffer = ByteArrayOutputStream()
                zis.copyTo(buffer)
                zis.closeEntry()
                return buffer.toByteArray()
            }
        }
    }
}
