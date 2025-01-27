package com.dugaza.letsdrive.util

import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

object MessageConverter {
    private val messageSource: ResourceBundleMessageSource = ResourceBundleMessageSource().apply {
        setBasename("messages/messages")
        setDefaultEncoding("UTF-8")
    }

    private val locale: Locale
        get() = LocaleContextHolder.getLocale()

    fun getMessage(code: String, vararg args: Any?): String {
        return messageSource.getMessage(code, args, locale)
    }
}
