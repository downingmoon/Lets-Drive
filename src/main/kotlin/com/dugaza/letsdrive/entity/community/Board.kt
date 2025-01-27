package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*

@Entity
@Table(name = "community_board")
class Board(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    var file: FileMaster,

    @OneToOne
    @JoinColumn(name = "vote_id", nullable = true)
    val vote: Vote,

    // TODO: Enum 생성 후 변경
    // @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val boardType: String,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = true)
    var views: Long,

    @Column(nullable = true)
    var isDisplayed: Boolean,
) : BaseEntity() {
    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun changeContent(newContent: String) {
        content = newContent
    }
}