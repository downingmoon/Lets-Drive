package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "community_vote_option")
class VoteOption(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vote_id")
    val vote: Vote,

    @Column(name = "option_content", nullable = false)
    val content: String
) : BaseEntity() {
}