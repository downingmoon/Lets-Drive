package com.dugaza.letsdrive.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@ActiveProfiles("test")
class ExceptionTest {
    @Test
    fun `공통 예외처리 응답 테스트`() {
        val ex =
            assertThrows<BusinessException> {
                throw BusinessException(ErrorCode.USER_NOT_FOUND)
            }
        assertEquals("유저를 찾을수 없습니다.", ex.message)
    }

    @Test
    fun `공통 예외처리 없는 에러코드 테스트`() {
        val ex =
            assertThrows<BusinessException> {
                throw BusinessException(ErrorCode.FOO)
            }
        assertEquals("정의되지 않은 에러코드입니다.", ex.message)
    }
}
