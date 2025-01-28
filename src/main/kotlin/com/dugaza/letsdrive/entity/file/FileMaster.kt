package com.dugaza.letsdrive.entity.file

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.user.User
import jakarta.persistence.*

@Entity
@Table(
    name = "file_master",
    indexes = [
        Index(name = "idx_file_master_user_id", columnList = "user_id"),
    ],
)
class FileMaster(
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    val userId: User,
) : BaseEntity()
