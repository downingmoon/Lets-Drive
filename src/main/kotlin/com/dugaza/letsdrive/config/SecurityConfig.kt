package com.dugaza.letsdrive.config

import com.dugaza.letsdrive.handler.oauth2.CustomOAuth2SuccessHandler
import com.dugaza.letsdrive.repository.user.UserRepository
import com.dugaza.letsdrive.service.user.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val userRepository: UserRepository,
) {
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_OAUTH2_TEMP")
    }

    @Bean
    fun roleHierarchyAuthoritiesMapper(roleHierarchy: RoleHierarchy): RoleHierarchyAuthoritiesMapper {
        return RoleHierarchyAuthoritiesMapper(roleHierarchy)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val roleHierarchy = roleHierarchy()
        val authoritiesMapper = roleHierarchyAuthoritiesMapper(roleHierarchy)

        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/", "/api/users/login", "/api/users/signup", "/error").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().hasRole("USER")
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/api/users/login")
                    .userInfoEndpoint { userInfo ->
                        userInfo
                            .userService(customOAuth2UserService)
                            .userAuthoritiesMapper(authoritiesMapper)
                    }
                    .successHandler(CustomOAuth2SuccessHandler(userRepository))
            }
            .logout { logout ->
                logout.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
            }
            .csrf {
                it.disable()
            }
        return http.build()
    }
}
