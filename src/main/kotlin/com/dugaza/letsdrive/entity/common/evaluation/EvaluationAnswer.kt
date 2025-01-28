package com.dugaza.letsdrive.entity.common.evaluation

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "common_evaluation_answer")
class EvaluationAnswer(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    val question: EvaluationQuestion,
    @Column(nullable = false)
    val answer: String,
) : BaseEntity()
