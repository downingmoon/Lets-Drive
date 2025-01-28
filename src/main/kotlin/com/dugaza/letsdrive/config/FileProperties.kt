package com.dugaza.letsdrive.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
class FileProperties {
    var maxSize: Long = 10 * 1024 * 1024L
    var imageExtensions: String = "jpg,jpeg,png,gif,bmp"
    var uncompressedExtensions: String = "bmp"
    var allowedExtensions: String = "mp4"
    var uploadRoot: String = "uploads"

    fun imageExtensionSet(): Set<String> = imageExtensions.split(",").map { it.trim().lowercase() }.toSet()

    fun uncompressedExtensionSet(): Set<String> = uncompressedExtensions.split(",").map { it.trim().lowercase() }.toSet()

    fun allowedExtensionSet(): Set<String> = allowedExtensions.split(",").map { it.trim().lowercase() }.toSet()
}
