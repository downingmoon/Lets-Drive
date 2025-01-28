package com.dugaza.letsdrive.entity.file

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "file_master",
    indexes = [
        Index(name = "idx_file_master_user_id", columnList = "user_id"),
    ],
)
class FileMaster(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
) : BaseEntity()