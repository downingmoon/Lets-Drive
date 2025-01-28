package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn

@Entity
@Table(
    name = "community_vote_option",
    indexes = [
        Index(name = "idx_community_vote_option_vote_id", columnList = "vote_id"),
    ]
)
class VoteOption(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    val vote: Vote,

    @Column(name = "option_content", nullable = false)
    val content: String
) : BaseEntity() {
}