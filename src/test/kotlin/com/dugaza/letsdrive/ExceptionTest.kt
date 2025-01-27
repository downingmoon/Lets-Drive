package com.dugaza.letsdrive

import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class ExceptionTest {

    @Test
    fun `공통 예외처리 응답 테스트`() {
        val ex = assertThrows<BusinessException> {
            throw BusinessException(ErrorCode.USER_NOT_FOUND)
        }
        assertEquals("유저를 찾을수 없습니다.", ex.message)
    }
}