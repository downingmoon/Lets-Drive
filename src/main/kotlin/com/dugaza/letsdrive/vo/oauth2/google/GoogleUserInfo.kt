package com.dugaza.letsdrive.vo.oauth2.google

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleUserInfo(
    val sub: String,
    val email: String,
    val name: String?,
    val picture: String?,
    @JsonProperty("email_verified")
    val emailVerified: Boolean?,
)
