package com.turkcell.spring_starter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.spring_starter.dto.RegisterRequest;
import com.turkcell.spring_starter.service.UserServiceImpl;

@RequestMapping("/api/users")
@RestController
public class UsersController {
    private final UserServiceImpl userService;

    public UsersController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@RequestBody RegisterRequest registerRequest)
    {
        this.userService.registerUser(registerRequest);
    }
}
