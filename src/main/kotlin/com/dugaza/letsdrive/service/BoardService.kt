package com.dugaza.letsdrive.service

import com.dugaza.letsdrive.dto.community.BoardListResponse
import com.dugaza.letsdrive.dto.community.BoardResponse
import com.dugaza.letsdrive.dto.community.BoardVo
import com.dugaza.letsdrive.entity.community.Board
import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import com.dugaza.letsdrive.repository.BoardRepository
import com.dugaza.letsdrive.repository.CommentRepository
import com.dugaza.letsdrive.repository.FileMasterRepository
import com.dugaza.letsdrive.repository.VoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoardService(
    private val userService: UserService,
    private val boardRepository: BoardRepository,
    private val fileMasterRepository: FileMasterRepository,
    private val voteRepository: VoteRepository,
    private val commentRepository: CommentRepository,
) {
    fun getAllBoards(): List<BoardListResponse> {
        return boardRepository.findAllBoardList()
    }

    @Transactional
    fun saveBoardProcess(boardVo: BoardVo): BoardResponse {
        val board: Board
        var commentCount: Long = 0
        if (boardVo.id == null) {
            val user: User = userService.getUserById(boardVo.userId)
            board = Board.createBoard(boardVo, user, null, null)
        } else {
            board =
                boardRepository.findById(boardVo.id).orElseThrow {
                    BusinessException(ErrorCode.BOARD_NOT_FOUND)
                }
            board.update(boardVo.title, boardVo.content)

            commentCount = commentRepository.countByBoard(board)
        }
        return BoardResponse.of(boardRepository.save(board), commentCount)
    }
}
