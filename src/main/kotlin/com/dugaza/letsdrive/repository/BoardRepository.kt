package com.dugaza.letsdrive.repository

import com.dugaza.letsdrive.dto.community.BoardListResponse
import com.dugaza.letsdrive.entity.community.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface BoardRepository : JpaRepository<Board, UUID> {
    @Query(
        "SELECT new com.dugaza.letsdrive.dto.community.BoardListResponse(b.id, b.title, b.boardType, b.views, b.createdAt, count(c.id)) " +
            "from Board b " +
            "left join Comment c ON b.id = c.board.id " +
            "group by b.id, b.title, b.boardType, b.views, b.createdAt",
    )
    fun findAllBoardList(): List<BoardListResponse>
}
