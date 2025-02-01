package com.dugaza.letsdrive.service.user

import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.exception.BusinessException
import com.dugaza.letsdrive.exception.ErrorCode
import com.dugaza.letsdrive.repository.user.UserRepository
import com.dugaza.letsdrive.vo.oauth2.UserInfo
import com.dugaza.letsdrive.vo.oauth2.google.GoogleUserInfo
import com.dugaza.letsdrive.vo.oauth2.kakao.KakaoUserInfo
import com.dugaza.letsdrive.vo.oauth2.naver.NaverUserInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val objectMapper =
        ObjectMapper()
            .registerKotlinModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId.lowercase()
        val provider =
            when (registrationId) {
                "google" -> AuthProvider.GOOGLE
                "naver" -> AuthProvider.NAVER
                "kakao" -> AuthProvider.KAKAO
                else -> throw BusinessException(ErrorCode.UNSUPPORTED_AUTH_PROVIDER)
            }

        val userNameAttributeName =
            userRequest.clientRegistration
                .providerDetails.userInfoEndpoint.userNameAttributeName ?: "sub"

        val attributes = oAuth2User.attributes

        val userInfo = extractUserInfo(provider, attributes)

        val authorities = listOf(SimpleGrantedAuthority("ROLE_OAUTH2_TEMP"))
        val mergedAttributes = attributes.toMutableMap()
        mergedAttributes["customUserInfo"] = userInfo
        mergedAttributes["provider"] = provider

        return DefaultOAuth2User(
            authorities,
            mergedAttributes,
            userNameAttributeName,
        )
    }

    private fun extractUserInfo(
        provider: AuthProvider,
        attributes: Map<String, Any>,
    ): UserInfo {
        return when (provider) {
            AuthProvider.GOOGLE -> {
                val googleUserInfo = objectMapper.convertValue(attributes, GoogleUserInfo::class.java)
                val sub = googleUserInfo.sub
                val email = googleUserInfo.email
                val name = googleUserInfo.name

                UserInfo(sub, email, name)
            }
            AuthProvider.NAVER -> {
                val naverUserInfo = objectMapper.convertValue(attributes, NaverUserInfo::class.java)
                val id = naverUserInfo.response.id
                val email = naverUserInfo.response.email
                val nickname = naverUserInfo.response.name

                UserInfo(id, email, nickname)
            }
            AuthProvider.KAKAO -> {
                val kakaoUserInfo = objectMapper.convertValue(attributes, KakaoUserInfo::class.java)
                val id = kakaoUserInfo.id
                val email =
                    if (!kakaoUserInfo.kakaoAccount.emailNeedsAgreement && kakaoUserInfo.kakaoAccount.isEmailValid) {
                        kakaoUserInfo.kakaoAccount.email
                    } else {
                        null
                    }
                val nickname =
                    if (!kakaoUserInfo.kakaoAccount.profileNicknameNeedsAgreement &&
                        !kakaoUserInfo.kakaoAccount.profile.isDefaultNickname
                    ) {
                        kakaoUserInfo.kakaoAccount.profile.nickname
                    } else {
                        null
                    }

                UserInfo(id, email, nickname)
            }
        }
    }
}
