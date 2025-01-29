package com.dugaza.letsdrive.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale

@Configuration
class LocaleConfig {
    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.setDefaultLocale(Locale.KOREAN)
        return resolver
    }
}
