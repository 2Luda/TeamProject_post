package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.LoginRequestDto;
import com.sparta.teamproject_post.dto.SignupRequestDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Jwtutil jwtutil;

    public StatusResponseDto signup(SignupRequestDto signupRequestDto, UserRoleEnum role) {
        Optional<User> byUsername = userRepository.findByUsername(signupRequestDto.getUsername());
        if (byUsername.isPresent()) {
            return new StatusResponseDto("Id가 중복됩니다.", 400);
        }
        User user = new User(signupRequestDto.getUsername(), signupRequestDto.getPassword(), role);
        userRepository.save(user);
        return new StatusResponseDto("가입이 완료되었습니다.", 200);
    }

    public String login(LoginRequestDto loginRequestDto) {
        Optional<User> byUsername = userRepository.findByUsername(loginRequestDto.getUsername());
        if (!byUsername.isPresent()) {
            return "없는 사용자 입니다.";
        }
        User user = byUsername.get();

        if (user.getPassword().equals(loginRequestDto.getPassword())) {
            String token = jwtutil.createToken(user.getUsername(), user.getRole());
            return token;
        } else {
            return "PW가 일치하지 않습니다.";
        }
    }
}