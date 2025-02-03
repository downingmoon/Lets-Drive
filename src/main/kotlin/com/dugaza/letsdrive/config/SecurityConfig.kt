package com.dugaza.letsdrive.config

import com.dugaza.letsdrive.service.user.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/", "/api/auth/users/login", "/error").permitAll()
                    .requestMatchers("/api/users/random-nickname", "/api/files/default-profile-image").hasAnyRole("USER", "UNVERIFIED_USER")
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().hasRole("USER")
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .loginPage("/api/auth/users/login")
                    .userInfoEndpoint { userInfo ->
                        userInfo
                            .userService(customOAuth2UserService)
                    }
                    .successHandler { _, response, _ ->
                        response.sendRedirect("/")
                    }
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
