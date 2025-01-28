package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    name = "community_comment",
    indexes = [
        Index(name = "idx_community_comment_user_id", columnList = "user_id"),
        Index(name = "idx_community_comment_board_id", columnList = "board_id"),
    ],
)
class Comment(
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    val board: Board,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @ManyToOne
    @JoinColumn(name = "parent_comment_id", nullable = true)
    val parentComment: Comment?,
    @Column(nullable = false)
    var content: String,
    var isDisplayed: Boolean = true,
) : BaseEntity() {
    fun changeContent(content: String) {
        this.content = content
    }

    fun changeDisplayed(displayed: Boolean) {
        this.isDisplayed = displayed
    }
}
