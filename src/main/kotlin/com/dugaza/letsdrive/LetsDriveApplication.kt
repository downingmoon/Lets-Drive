package com.dugaza.letsdrive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LetsDriveApplication

fun main(args: Array<String>) {
    runApplication<LetsDriveApplication>(*args)
}
