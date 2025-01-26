package com.dugaza.letsdrive.entity.user

import com.dugaza.letsdrive.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["provider", "providerId"])
    ]
)
class User(
    @Column(nullable = false, unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: AuthProvider,

    @Column(nullable = false)
    val providerId: String,

    @Column(nullable = true)
    var name: String? = null,

    @Column(nullable = true)
    var phoneNumber: String? = null,

    // role도 추가해야함
//    @Column(nullable = true)
//    var profileImageUrl: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    var lastLoginAt : LocalDateTime? = null
): BaseEntity() {
    fun login() {
        lastLoginAt = LocalDateTime.now()
        if (status == UserStatus.DORMANT) {
            status = UserStatus.ACTIVE
        }
    }

    fun changeName(newName: String) {
        name = newName
    }

    fun toDormant() {
        status = UserStatus.DORMANT
    }

    fun withdraw() {
        status = UserStatus.WITHDRAWN
        delete()
    }
}