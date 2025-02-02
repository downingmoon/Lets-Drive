package com.dugaza.letsdrive.vo.oauth2

import com.dugaza.letsdrive.exception.ErrorCode
import com.dugaza.letsdrive.validator.CustomValidator
import org.springframework.web.multipart.MultipartFile

class SignupForm(
    @field:CustomValidator.NotBlank(ErrorCode.DEFAULT_NOT_BLANK_MESSAGE)
    val email: String,
    @field:CustomValidator.NotBlank(ErrorCode.DEFAULT_NOT_BLANK_MESSAGE)
    val nickname: String,
    val profileImage: MultipartFile? = null,
)
