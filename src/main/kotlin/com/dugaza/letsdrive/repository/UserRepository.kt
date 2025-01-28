package com.dugaza.letsdrive.repository

import com.dugaza.letsdrive.entity.user.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, UUID> {
}