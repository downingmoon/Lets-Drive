package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.converter.BoardTypeConverter
import com.dugaza.letsdrive.dto.community.BoardVo
import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(
    name = "community_board",
    indexes = [
        Index(name = "idx_community_board_user_id", columnList = "user_id"),
    ],
)
@SQLRestriction("is_displayed = 1")
class Board(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = true)
    var file: FileMaster?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = true)
    val vote: Vote?,
    @Convert(converter = BoardTypeConverter::class)
    @Column(nullable = false)
    val boardType: BoardType,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var views: Long = 0L,

    @Column(nullable = false)
    var isDisplayed: Boolean = false,
) : BaseEntity() {
    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun changeContent(newContent: String) {
        content = newContent
    }

    fun update(
        title: String,
        content: String,
    ) {
        this.title = title
        this.content = content
    }

    fun increaseView() {
        views += 1
    }

    companion object {
        fun createBoard(
            boardVo: BoardVo,
            user: User,
            file: FileMaster?,
            vote: Vote?,
        ): Board {
            return Board(
                user,
                file,
                vote,
                BoardType.valueOf(boardVo.boardType),
                boardVo.title,
                boardVo.content,
                views = 0,
                isDisplayed = true,
            )
        }
    }
}