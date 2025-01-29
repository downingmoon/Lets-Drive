package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "community_vote_result")
class VoteResult(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    val option: VoteOption,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseEntity()
