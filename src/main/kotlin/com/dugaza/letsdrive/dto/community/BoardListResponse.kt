package com.dugaza.letsdrive.dto.community

import com.dugaza.letsdrive.entity.community.BoardType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

data class BoardListResponse(
    val id: UUID,
    val title: String,
    val boardType: BoardType,
    val views: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    val commentCount: Long,
)
