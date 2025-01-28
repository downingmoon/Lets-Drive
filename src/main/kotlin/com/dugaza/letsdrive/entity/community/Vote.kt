package com.dugaza.letsdrive.entity.community

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "community_vote")
class Vote(
    @Column(nullable = false, length = 255)
    var title: String,
) : BaseEntity()
