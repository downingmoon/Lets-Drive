package com.dugaza.letsdrive.controller

import com.dugaza.letsdrive.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
)
