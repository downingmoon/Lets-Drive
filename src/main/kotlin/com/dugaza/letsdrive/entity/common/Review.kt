package com.dugaza.letsdrive.entity.common;

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.common.evaluation.Evaluation
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "common_review",
    indexes = [
        Index(name = "idx_common_review_target_id", columnList = "target_id"),
        Index(name = "idx_common_review_user_id", columnList = "user_id"),
        Index(name = "idx_common_review_evaluation_id", columnList = "evaluation_id"),
        Index(name = "idx_common_review_file_id", columnList = "file_id"),
    ],
)
class Review(
    @Column(name = "target_id", nullable = false)
    val target: UUID,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    val evaluation: Evaluation,

    @OneToOne
    @JoinColumn(name = "file_id", nullable = true)
    var file: FileMaster,

    @Column(nullable = false)
    var score: Double = 0.0,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var isDisplayed: Boolean = false
) : BaseEntity() {
}
