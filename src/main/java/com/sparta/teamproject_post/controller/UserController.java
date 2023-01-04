package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.UserDto;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.exception.CustomException;
import com.sparta.teamproject_post.jwt.JwtUtil;
import com.sparta.teamproject_post.repository.UserRepository;
import com.sparta.teamproject_post.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody @Validated UserDto.signUpRequestDto signUpRequestDto, BindingResult bindingresult){

        //유효성 검사 실패할 경우 에러메세지 반환
        if(bindingresult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingresult.getAllErrors().toString());
        }

        //이미 존재한 user인지 확인
        Optional<User> check_result = userRepository.findByUsername(signUpRequestDto.getUsername());
        check_result.ifPresent(m -> {
            throw new CustomException(DUPLICATE_USERNAME);
        });

        UserRoleEnum role = UserRoleEnum.USER;

        if(signUpRequestDto.isAdmin()){
            if(!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new CustomException(ADMIN_PASSWORD_NOT_FOUND);
            }
            role = UserRoleEnum.ADMIN;
        }

        userService.signUp(signUpRequestDto, role);
        return new ResponseEntity<>("회원가입에 성공하였습니다", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, HttpServletResponse response){

        UserDto.loginResponseDto user = userService.login(username, password);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseEntity("로그인에 성공하였습니다.", HttpStatus.OK);
    }
}
