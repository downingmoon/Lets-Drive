package com.dugaza.letsdrive.service.auth

import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.entity.user.Role
import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.service.file.FileService
import com.dugaza.letsdrive.service.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class AuthService(
    private val fileService: FileService,
    private val userService: UserService,
) {
    @Transactional
    fun signup(
        provider: AuthProvider,
        providerId: String,
        email: String,
        nickname: String,
        profileImage: MultipartFile?,
    ): User {
        val user =
            userService.createUser(
                User(
                    provider = provider,
                    providerId = providerId,
                    email = email,
                    nickname = nickname,
                ),
            )
        user.addRole(Role.USER)

        val fileMaster =
            if (profileImage == null || profileImage.isEmpty) {
                fileService.getDefaultImage(user.id!!)
            } else {
                fileService.uploadFile(user.id!!, listOf(profileImage)).first
            }
        user.changeProfileImage(fileMaster)
        user.login()

        return user
    }
}
