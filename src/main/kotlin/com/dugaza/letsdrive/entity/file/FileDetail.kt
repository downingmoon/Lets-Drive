package com.dugaza.letsdrive.entity.file

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "file_detail",
    indexes = [
        Index(name = "idx_file_detail_file_master_id", columnList = "file_master_id"),
        Index(name = "idx_file_detail_file_hash", columnList = "file_hash"),
    ],
)
class FileDetail(
    @Column(name = "file_master_id", nullable = false)
    val fileMasterId: UUID,
    @Column(nullable = false)
    val originalName: String,
    @Column(nullable = false)
    val storedName: String,
    @Column(nullable = false)
    val storedPath: String,
    @Column(nullable = false)
    val originalSize: Long,
    @Column(nullable = false)
    val storedSize: Long,
    @Column(nullable = false)
    val originalExtension: String,
    @Column(nullable = false)
    val storedExtension: String,
    @Column(nullable = false, length = 255)
    val mimeType: String,
    @Column(nullable = false, length = 64, unique = true)
    val fileHash: String,
    @Column(nullable = true)
    var thumbnailPath: String? = null,
    @Column(nullable = false)
    var compressed: Boolean = false,
) : BaseEntity()
