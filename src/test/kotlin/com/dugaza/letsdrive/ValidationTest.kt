package com.dugaza.letsdrive

import jakarta.validation.Validator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import java.util.UUID
import kotlin.test.assertContains

@ActiveProfiles("test")
class ValidationTest(
    @Autowired val validator: Validator,
) {
    @Test
    fun `Spring Validation 다국어 메시지 테스트`() {
        val vo = ValidationTestVo(UUID.randomUUID(), "")
        val validate = validator.validate(vo)
        val iterator = validate.iterator()
        val messages: ArrayList<String> = ArrayList()
        while (iterator.hasNext()) {
            val next = iterator.next()
            messages.add(next.message)
            println("message: ${next.message}")
        }
        assertContains(messages, ("지원하지 않는 확장자입니다."))
    }
}
