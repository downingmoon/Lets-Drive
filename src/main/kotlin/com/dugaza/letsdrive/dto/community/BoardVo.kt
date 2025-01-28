package com.dugaza.letsdrive.dto.community

import com.dugaza.letsdrive.converter.BoardTypeConverter
import jakarta.persistence.Convert
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

class BoardVo(
    val id: UUID?,
    // TODO: validation 다국어
    @field:NotBlank(message = "User id required.")
    val userId: UUID,
    @field:NotBlank(message = "Board type required.")
    @Convert(converter = BoardTypeConverter::class)
    val boardType: String,
    @field:NotBlank(message = "Title required.")
    val title: String,
    @field:NotBlank(message = "Content required.")
    val content: String,
    val files: List<MultipartFile>?,
    // TODO: VoteVo
)
