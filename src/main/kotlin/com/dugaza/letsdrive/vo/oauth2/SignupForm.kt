package com.dugaza.letsdrive.vo.oauth2

import org.springframework.web.multipart.MultipartFile

class SignupForm(
    val email: String,
    val nickname: String,
    val phoneNumber: String,
    val profileImage: MultipartFile? = null,
)
