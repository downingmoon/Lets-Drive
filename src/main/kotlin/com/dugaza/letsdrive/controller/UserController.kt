package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.dto.user.RandomNicknameResponse
import com.dugaza.letsdrive.service.user.UserService
import com.dugaza.letsdrive.util.generateRandomNickname
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/random-nickname")
    fun getRandomNickname(): ResponseEntity<RandomNicknameResponse> {
        return ResponseEntity.ok(RandomNicknameResponse(generateRandomNickname()))
    }
}
