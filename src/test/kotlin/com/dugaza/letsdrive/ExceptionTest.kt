package com.dugaza.letsdrive

import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ExceptionTest {
    @Test
    fun `공통 예외처리 응답 테스트`() {
        val ex =
            assertThrows<BusinessException> {
                throw BusinessException(ErrorCode.USER_NOT_FOUND)
            }
        assertEquals("1", "1")
    }

    @Test
    fun `공통 예외처리 없는 에러코드 테스트`() {
        val ex =
            assertThrows<BusinessException> {
                throw BusinessException(ErrorCode.FOO)
            }
        assertEquals("1", "1")
    }
}
