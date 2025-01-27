package com.dugaza.letsdrive.entity.common

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "common_like")
class Like(
    // MEMO: Board만 특정?
    @Column(name = "target_id", nullable = false)
    val target: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) : BaseEntity() {
}