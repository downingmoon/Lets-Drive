package com.dugaza.letsdrive.vo.oauth2.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoUserInfo(
    val id: String,
)
