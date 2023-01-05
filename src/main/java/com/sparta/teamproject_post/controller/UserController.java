package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.LoginRequestDto;
import com.sparta.teamproject_post.dto.SignupRequestDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private static final String ADMIN_PASSWORD = "s$pSGgOIfl042KfBwlgGGSLQpmvb";
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public StatusResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (signupRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                role = UserRoleEnum.ADMIN;
            } else {
                return new StatusResponseDto("Admin PW가 틀렸습니다.", 400);
            }
        }
        return userService.signup(signupRequestDto, role);

    }

    @ResponseBody
    @PostMapping("/login")
    public StatusResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String token = userService.login(loginRequestDto);
        if (token.equals("없는 사용자 입니다.")) {
            return new StatusResponseDto(token,200);
        } else if (token.equals("PW가 일치하지 않습니다.")) {
            return new StatusResponseDto(token,200);

        }else {
            response.addHeader(Jwtutil.AUTHORIZATION_HEADER, token);
            return new StatusResponseDto("로그인 성공!",200);
        }
    }
}