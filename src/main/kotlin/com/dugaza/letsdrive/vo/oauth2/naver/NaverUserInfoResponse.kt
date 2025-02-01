package com.dugaza.letsdrive.vo.oauth2.naver

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class NaverUserInfoResponse(
    val email: String,
    @JsonProperty("profile_image")
    val profileImage: String,
    val id: String,
    val name: String,
    val mobile: String,
)
