package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.config.FileProperties
import com.dugaza.letsdrive.entity.user.AuthProvider
import com.dugaza.letsdrive.entity.user.User
import com.dugaza.letsdrive.service.file.FileService
import com.dugaza.letsdrive.service.user.UserService
import com.dugaza.letsdrive.vo.oauth2.SignupForm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val fileService: FileService,
) {
    private val fileProperties = FileProperties()

    @GetMapping("/signup")
    fun signup(
        request: HttpServletRequest,
        model: Model,
    ): String {
        val email = request.session.getAttribute("OAUTH_EMAIL") as? String
        val nickname = request.session.getAttribute("OAUTH_NICKNAME") as? String

        model.addAttribute("profileImageDetailId", fileProperties.defaultImageDetailId)
        model.addAttribute("signUpForm", SignupForm("", "", ""))

        return "signup"
    }

    // 여기서 프로필 이미지 받아야할듯
    @PostMapping("/signup")
    fun signupSubmit(
        @ModelAttribute
        form: SignupForm,
        request: HttpServletRequest,
    ): String {
        val provider = request.session.getAttribute("OAUTH_PROVIDER") as AuthProvider
        val providerId = request.session.getAttribute("OAUTH_PROVIDER_ID") as String

        val user = userService.signup(provider, providerId, form.email, form.nickname, form.phoneNumber)
        setUserAuthentication(user)

        // todo : 지원하지 않는 확장자 올렸을 시 트랜잭션이 분리되어있어서 유저 생성됨, provider에서 보내준 기본 정보도 활용 가능하게 하기
        val fileMaster =
            if (form.profileImage == null || form.profileImage.isEmpty) {
                fileService.getDefaultImage(user.id!!)
            } else {
                fileService.uploadFile(user.id!!, listOf(form.profileImage)).first
            }

        userService.changeProfileImage(user.id!!, fileMaster)

        return "redirect:/"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login-page"
    }

    private fun setUserAuthentication(user: User) {
        val authorities =
            listOf<GrantedAuthority>(
                SimpleGrantedAuthority("ROLE_${user.role.name}"),
            )

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
