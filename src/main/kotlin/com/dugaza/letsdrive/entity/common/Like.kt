package com.dugaza.letsdrive.entity.common

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(
    name = "common_like",
    indexes = [
        Index(name = "idx_common_like_target_id", columnList = "target_id"),
        Index(name = "idx_common_like_user_id", columnList = "user_id"),
    ]
)
class Like(
    @Column(name = "target_id", nullable = false)
    val target: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseEntity() {
}