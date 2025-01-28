package com.dugaza.letsdrive.exception

import com.dugaza.letsdrive.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        val body =
            ErrorResponse(
                code = errorCode.code,
                message = e.message!!,
            )
        return ResponseEntity.status(errorCode.status).body(body)
    }
}
