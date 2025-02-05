package com.dugaza.letsdrive.entity.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import java.util.UUID

class CustomOAuth2User(
    private val userId: UUID,
    authorities: Collection<GrantedAuthority>,
    attributes: Map<String, Any>,
    private val nameAttributeKey: String,
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey) {
    companion object {
        private const val serialVersionUID = 1L
    }

    fun getUserId(): UUID = userId

    fun getNameAttributeKey(): String = nameAttributeKey
}
