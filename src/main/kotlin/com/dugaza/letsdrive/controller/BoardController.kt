package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.dto.community.BoardListResponse
import com.dugaza.letsdrive.dto.community.BoardResponse
import com.dugaza.letsdrive.dto.community.BoardVo
import com.dugaza.letsdrive.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/boards")
class BoardController(
    private val boardService: BoardService,
) {
    @GetMapping
    fun list(): ResponseEntity<List<BoardListResponse>> {
        return ResponseEntity.ok(boardService.getAllBoards())
    }

    @PostMapping
    fun insert(
        @RequestBody boardVo: BoardVo,
    ): ResponseEntity<BoardResponse> {
        return ResponseEntity.ok(boardService.saveBoardProcess(boardVo))
    }
}
