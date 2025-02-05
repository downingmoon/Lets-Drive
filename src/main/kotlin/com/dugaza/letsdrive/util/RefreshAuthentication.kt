package com.dugaza.letsdrive.util

import com.dugaza.letsdrive.entity.user.CustomOAuth2User
import com.dugaza.letsdrive.entity.user.UserRole
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

fun refreshAuthentication(
    oldOAuthUser: CustomOAuth2User,
    roles: Set<UserRole>,
) {
    val authorities = roles.map { SimpleGrantedAuthority("ROLE_${it.role}") }
    val newPrincipal = CustomOAuth2User(oldOAuthUser.getUserId(), authorities, oldOAuthUser.attributes, oldOAuthUser.getNameAttributeKey())
    val newAuth = UsernamePasswordAuthenticationToken(newPrincipal, null, authorities)

    SecurityContextHolder.getContext().authentication = newAuth
}
