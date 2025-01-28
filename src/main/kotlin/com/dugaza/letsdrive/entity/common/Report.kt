package com.dugaza.letsdrive.entity.common

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import java.util.*

@Entity
@Table(
    name = "common_report",
    indexes = [
        Index(name = "idx_common_review_target_id", columnList = "target_id"),
        Index(name = "idx_common_review_user_id", columnList = "user_id"),
    ]
)
class Report(
    @Column(nullable = false)
    val targetId: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    // TODO: Enum 작성 후 변경
    // @Enumerated(EnumType.STRING)
    @Column(name = "report_reason_code", nullable = false)
    val reasonCode: String,

    @Column(name = "report_reason", nullable = false)
    val reason: String
) : BaseEntity() {
}