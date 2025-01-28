package com.dugaza.letsdrive.dto.file

import java.util.UUID

data class UploadResponse(
    val fileMasterId: UUID,
    val details: List<FileDetailDto>,
)
