package com.dugaza.letsdrive.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
) {
    // File Error
    FILE_SIZE_TOO_LARGE(HttpStatus.BAD_REQUEST, "FILE_001"),
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "FILE_002"),
    IMAGE_THUMBNAIL_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_003"),
    FILE_COMPRESSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_004"),
    FILE_DECOMPRESSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_005"),
    NOT_FOUND_FILE_DETAIL(HttpStatus.NOT_FOUND, "FILE_006"),
    INVALID_IMAGE_DATA(HttpStatus.BAD_REQUEST, "FILE_007"),

    // User Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001"),

    // Board Error
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD_001"),

    // System Error
    INVALID_ERROR_CODE(HttpStatus.BAD_REQUEST, "SYSTEM_001"),

    // For test
    FOO(HttpStatus.INTERNAL_SERVER_ERROR, "FOO_001"),
}
