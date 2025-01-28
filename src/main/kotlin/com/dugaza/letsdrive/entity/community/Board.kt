package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.OneToOne
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType

@Entity
@Table(
    name = "community_board",
    indexes = [
        Index(name = "idx_community_board_user_id", columnList = "user_id"),
    ]
)
class Board(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    var file: FileMaster,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = true)
    val vote: Vote?,

    @Enumerated(EnumType.STRING)
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
}