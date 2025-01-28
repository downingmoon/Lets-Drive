package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.dto.file.FileDetailDto
import com.dugaza.letsdrive.dto.file.UploadResponse
import com.dugaza.letsdrive.service.FileService
import java.nio.charset.StandardCharsets
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import org.springframework.http.ContentDisposition

@RestController
@RequestMapping("/api/files")
class FileController(
    private val fileService: FileService,
) {
    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("userId") userId: UUID,
        @RequestParam("files") files: List<MultipartFile>,
    ): ResponseEntity<UploadResponse> {
        val (master, details) = fileService.uploadFile(userId, files)
        val response = UploadResponse(master.id!!, details.map { FileDetailDto.of(it) })
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{detailId}/download")
    fun downloadFile(
        @PathVariable("detailId") detailId: UUID,
    ): ResponseEntity<ByteArrayResource> {
        val detail = fileService.getFileDetail(detailId)
        val fileBytes = fileService.downloadFile(detailId)

        val fileName = detail.originalName
        val mimeType = MediaType.parseMediaType(detail.mimeType)
        val resource = ByteArrayResource(fileBytes)
        val contentLength = fileBytes.size.toLong()

        val contentDisposition = ContentDisposition.builder("attachment")
            .filename(fileName, StandardCharsets.UTF_8)
            .build()

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
            .contentType(mimeType)
            .contentLength(contentLength)
            .body(resource)
    }
}
