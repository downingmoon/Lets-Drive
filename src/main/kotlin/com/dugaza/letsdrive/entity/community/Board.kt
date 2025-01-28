package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*

@Entity
@Table(
    name = "community_board",
    indexes = [
        Index(name = "idx_community_board_user_id", columnList = "user_id"),
        Index(name = "idx_community_board_file_id", columnList = "file_id"),
        Index(name = "idx_community_board_vote_id", columnList = "vote_id"),
    ]
)
class Board(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    var file: FileMaster,

    @OneToOne
    @JoinColumn(name = "vote_id", nullable = true)
    val vote: Vote?,

    // TODO: Enum 생성 후 변경
    // @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val boardType: String,

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