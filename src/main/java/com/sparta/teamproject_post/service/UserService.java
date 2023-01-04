package com.sparta.teamproject_post.service;
import com.sparta.teamproject_post.dto.SignupRequestDto;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.JwtUtil;
import com.sparta.teamproject_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public StatusResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;

        if (signupRequestDto.isAdmin()) {
            role = UserRoleEnum.ADMIN;
            User user = new User(username, password, role);
            userRepository.save(user);
            return new StatusResponseDto("200", "관리자로 회원가입 성공");
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return new StatusResponseDto("200", "회원가입 성공");
    }

    @Transactional(readOnly = true)
    public StatusResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return new StatusResponseDto("200", "로그인 성공");
    }
}