package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.SignupRequestDto;
import com.sparta.teamproject_post.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public StatusResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);

    }

    @ResponseBody
    @PostMapping("/login")
    public StatusResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}