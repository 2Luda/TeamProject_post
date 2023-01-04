package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.UserDto;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signUp(UserDto.signUpRequestDto signUpDto, UserRoleEnum role) {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        userRepository.save(new User(signUpDto.getUsername(), password, role));
    }

    @Transactional(readOnly = true)
    public UserDto.loginResponseDto login(String username, String password) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(PASSWORD_NOT_FOUND);
        }
        return new UserDto.loginResponseDto(user.getUsername(), user.getPassword(), user.getRole());
    }
}
