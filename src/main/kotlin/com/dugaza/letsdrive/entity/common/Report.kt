package com.dugaza.letsdrive.entity.common

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "common_report")
class Report(
    @Column(name = "target_id", nullable = false)
    val target: UUID,

    @ManyToOne(fetch = FetchType.EAGER)
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