package com.dugaza.letsdrive.validator

import com.dugaza.letsdrive.exception.BusinessException
import jakarta.validation.ConstraintViolation
import jakarta.validation.ValidationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ActiveProfiles("test")
class ValidationInMultiLanguageTest : ValidatorTestBase() {
    @Test
    fun `Custom NotNull Success`() {
        val vo = ValidationTestVo("", "this is a test", "abc", 10, 255, 10)
        val violations: Set<ConstraintViolation<ValidationTestVo>> = validator.validate(vo)
        assertEquals(0, violations.size)
    }

    @Test
    fun `Custom NotBlank Success`() {
        val vo = ValidationTestVo("", "this is a test", "abc", 10, 255, 10)
        val violations: Set<ConstraintViolation<ValidationTestVo>> = validator.validate(vo)
        assertEquals(0, violations.size)
    }

    @Test
    fun `Custom NotBlank Fail`() {
        val vo = ValidationTestVo("", "", "abc", 10, 255, 10)
        val ex = assertThrows<ValidationException> { validator.validate(vo) }
        assertTrue(ex.cause is BusinessException)
        assertEquals("필수 파라미터가 없거나 비어있습니다.", ex.cause!!.message)
    }
}
