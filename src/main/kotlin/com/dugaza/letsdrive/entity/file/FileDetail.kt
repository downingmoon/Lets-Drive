package com.dugaza.letsdrive.entity.file

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["file_hash"])
    ]
)
class FileDetail(
    @ManyToOne
    @JoinColumn(name = "file_master_id")
    val fileMaster: FileMaster,

    @Column(nullable = true)
    val originalName: String,

    @Column(nullable = true)
    val storedName: String,

    @Column(nullable = true)
    val storedPath: String,

    @Column(nullable = true)
    val originalSize: Long,

    @Column(nullable = true)
    val storedSize: Long,

    @Column(nullable = true)
    val mimeType: String,

    @Column(nullable = true)
    val fileHash: String,

    @Column(nullable = true)
    val thumbnailPath: String,

    @Column(nullable = true)
    val compressed: Boolean
) : BaseEntity() {
}