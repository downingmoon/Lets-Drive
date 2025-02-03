package com.dugaza.letsdrive.service.user

import com.dugaza.letsdrive.entity.file.FileMaster
import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import com.dugaza.letsdrive.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUserById(userId: UUID): User {
        return userRepository.findById(userId).orElseThrow {
            BusinessException(ErrorCode.USER_NOT_FOUND)
        }
    }

    @Transactional
    fun createUser(user: User): User {
        user.login()
        return userRepository.save(user)
    }

    @Transactional
    fun changeProfileImage(
        userId: UUID,
        fileMaster: FileMaster,
    ) {
        val user = getUserById(userId)
        user.changeProfileImage(fileMaster)
    }

    fun getUserByProvider(
        provider: AuthProvider,
        providerId: String,
    ): User {
        return userRepository.findUserByProviderAndProviderId(provider, providerId)
            ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)
    }
}
