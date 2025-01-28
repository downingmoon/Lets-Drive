package com.dugaza.letsdrive.repository

import com.dugaza.letsdrive.entity.community.Board
import com.dugaza.letsdrive.entity.community.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CommentRepository : JpaRepository<Comment, UUID> {
    fun countByBoard(board: Board): Long
}
