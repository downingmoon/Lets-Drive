package com.dugaza.letsdrive.vo.oauth2.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoProfile(
    val nickname: String,
    @JsonProperty("is_default_nickname")
    val isDefaultNickname: Boolean,
)
