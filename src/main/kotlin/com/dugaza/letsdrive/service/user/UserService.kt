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
    fun signup(
        provider: AuthProvider,
        providerId: String,
        email: String,
        nickname: String,
        phoneNumber: String,
    ): User {
        val user =
            User(
                provider = provider,
                providerId = providerId,
                email = email,
                nickname = nickname,
                phoneNumber = phoneNumber,
            )
        user.login()
        return userRepository.save(user)
    }

    @Transactional
    fun changeProfileImage(
        userId: UUID,
        fileMaster: FileMaster,
    ) {
        val user = getUserById(userId)
        user.profileImage = fileMaster
    }
}
