package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.config.FileProperties
import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.service.auth.AuthService
import com.dugaza.letsdrive.util.generateRandomNickname
import com.dugaza.letsdrive.vo.oauth2.SignupForm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    private val fileProperties = FileProperties()

    @GetMapping("/users/signup")
    fun signup(
        request: HttpServletRequest,
        model: Model,
    ): String {
        val email = request.session.getAttribute("OAUTH_EMAIL") as? String

        model.addAttribute("profileImageDetailId", fileProperties.defaultImageDetailId)
        model.addAttribute("signUpForm", SignupForm(email ?: "", generateRandomNickname()))

        return "signup"
    }

    // 여기서 프로필 이미지 받아야할듯
    @PostMapping("/users/signup")
    fun signupSubmit(
        @ModelAttribute
        form: SignupForm,
        request: HttpServletRequest,
    ): String {
        val provider = request.session.getAttribute("OAUTH_PROVIDER") as AuthProvider
        val providerId = request.session.getAttribute("OAUTH_PROVIDER_ID") as String

        val user = authService.signup(provider, providerId, form.email, form.nickname, form.profileImage)
        setUserAuthentication(user)

        return "redirect:/"
    }

    @GetMapping("/users/login")
    fun login(): String {
        return "login-page"
    }

    private fun setUserAuthentication(user: User) {
        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it.role.name}") }

        val authentication =
            UsernamePasswordAuthenticationToken(
                user.id!!,
                // 나중에 추가로 필요하면 추가
                null,
                authorities,
            )

        SecurityContextHolder.getContext().authentication = authentication
    }
}
