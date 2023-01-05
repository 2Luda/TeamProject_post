package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.LoginRequestDto;
import com.sparta.teamproject_post.dto.SignupRequestDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Jwtutil jwtutil;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능
    public StatusResponseDto signup(SignupRequestDto signupRequestDto, UserRoleEnum role) {
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        Optional<User> byUsername = userRepository.findByUsername(signupRequestDto.getUsername());
        if (byUsername.isPresent()) {
            return new StatusResponseDto("Id가 중복됩니다.", 400);
        }

        User user = new User(signupRequestDto.getUsername(), password, role);
        userRepository.save(user);
        return new StatusResponseDto("가입이 완료되었습니다.", 200);
    }

    // 로그인 기능
    public String login(LoginRequestDto loginRequestDto) {
        String password = loginRequestDto.getPassword();
        Optional<User> byUsername = userRepository.findByUsername(loginRequestDto.getUsername());
        if (!byUsername.isPresent()) {
            return "없는 사용자 입니다.";
        }
        User user = byUsername.get();

        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다");
        }
        String token = jwtutil.createToken(user.getUsername(), user.getRole());
        return token;
    }
}