package com.dugaza.letsdrive.dto.file

import com.dugaza.letsdrive.entity.file.FileDetail
import java.util.UUID

class FileDetailDto(
    val id: UUID,
    val originalName: String,
    val extension: String,
    val compressed: Boolean,
    val fileSize: Long,
    val thumbnailPath: String?,
) {
    companion object {
        fun of(entity: FileDetail): FileDetailDto {
            return FileDetailDto(
                id = entity.id!!,
                originalName = entity.originalName,
                extension = entity.originalExtension,
                compressed = entity.compressed,
                fileSize = entity.originalSize,
                thumbnailPath = entity.thumbnailPath,
            )
        }
    }
}
