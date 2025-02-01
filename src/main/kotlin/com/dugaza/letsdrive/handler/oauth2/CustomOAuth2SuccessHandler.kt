package com.dugaza.letsdrive.handler.oauth2

import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.repository.user.UserRepository
import com.dugaza.letsdrive.vo.oauth2.UserInfo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomOAuth2SuccessHandler(
    private val userRepository: UserRepository,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oauthUser = authentication.principal as DefaultOAuth2User
        val attributes = oauthUser.attributes
        val userInfo = attributes["customUserInfo"] as? UserInfo
        val provider = attributes["provider"] as? AuthProvider

        if (userInfo == null) {
            response.sendRedirect("/")
            return
        }

        userRepository.findUserByProviderId(userInfo.providerId)
            ?. let {
                it.login()
                response.sendRedirect("/")
                return
            }

        request.session.setAttribute("OAUTH_PROVIDER", provider)
        request.session.setAttribute("OAUTH_PROVIDER_ID", userInfo.providerId)
        request.session.setAttribute("OAUTH_EMAIL", userInfo.email)
        request.session.setAttribute("OAUTH_NICKNAME", userInfo.nickname)

        response.sendRedirect("/api/users/signup")
    }
}
