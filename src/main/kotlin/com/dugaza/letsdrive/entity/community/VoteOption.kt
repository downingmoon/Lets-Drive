package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.*

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