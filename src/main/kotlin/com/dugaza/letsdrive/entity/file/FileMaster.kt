package com.dugaza.letsdrive.entity.file

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.util.UUID

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
