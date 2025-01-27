package com.dugaza.letsdrive.entity.common;

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.common.evaluation.Evaluation
import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "common_review")
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

    @Column(nullable = true)
    var score: Double,

    @Column(nullable = true)
    var content: String,

    @Column(nullable = true)
    var isDisplayed: Boolean
) : BaseEntity() {
}
