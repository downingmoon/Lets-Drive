package com.dugaza.letsdrive.entity.user

import com.dugaza.letsdrive.entity.base.BaseEntity
import com.dugaza.letsdrive.entity.file.FileMaster
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(
    name = "user",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["provider", "provider_id"]),
    ],
)
class User(
    @Column(nullable = false, unique = true)
    val email: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: AuthProvider,
    @Column(nullable = false)
    val providerId: String,
    @Column(nullable = false)
    var nickname: String,
    @Column(nullable = false)
    var phoneNumber: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id", nullable = true)
    var profileImage: FileMaster? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,
    @Column(nullable = true)
    var lastLoginAt: LocalDateTime? = null,
) : BaseEntity() {
    fun login() {
        lastLoginAt = LocalDateTime.now()
        if (status == UserStatus.DORMANT) {
            status = UserStatus.ACTIVE
        }
    }

    fun changeName(newName: String) {
        nickname = newName
    }

    fun toDormant() {
        status = UserStatus.DORMANT
    }

    fun withdraw() {
        status = UserStatus.WITHDRAWN
        delete()
    }
}
