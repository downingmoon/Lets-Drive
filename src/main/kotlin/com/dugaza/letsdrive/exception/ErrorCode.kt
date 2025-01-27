package com.dugaza.letsdrive.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // File Error
    FILE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST, "FILE_001", "File size is too large"),
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "FILE_002", "Invalid file extension"),
    IMAGE_THUMBNAIL_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_003", "Image thumbnail generation failed"),
    FILE_COMPRESSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_004", "File compression failed"),
    FILE_DECOMPRESSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_005", "File decompression failed"),
    NOT_FOUND_FILE_DETAIL(HttpStatus.NOT_FOUND, "FILE_006", "File detail not found"),
    INVALID_IMAGE_DATA(HttpStatus.BAD_REQUEST, "FILE_007", "Invalid image data"),

    // User Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "User not found"),
}