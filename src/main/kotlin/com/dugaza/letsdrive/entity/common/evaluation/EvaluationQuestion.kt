package com.dugaza.letsdrive.entity.common.evaluation

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "common_evaluation_question")
class EvaluationQuestion(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluation_id", nullable = false)
    val evaluation: Evaluation,

    @Column(nullable = false)
    val question: String
) : BaseEntity() {
}