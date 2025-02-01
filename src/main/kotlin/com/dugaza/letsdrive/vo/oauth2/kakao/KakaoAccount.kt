package com.dugaza.letsdrive.vo.oauth2.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoAccount(
    val email: String,
    val profile: KakaoProfile,
    @JsonProperty("has_email")
    val hasEmail: Boolean,
    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean,
    @JsonProperty("is_email_verified")
    val isEmailVerified: Boolean,
    @JsonProperty("email_needs_agreement")
    val emailNeedsAgreement: Boolean,
    @JsonProperty("profile_nickname_needs_agreement")
    val profileNicknameNeedsAgreement: Boolean,
)
