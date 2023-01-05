package com.sparta.teamproject_post.security;

import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    // 데이터베이스에서 사용자 인증정보를 가져오는 역할
    // UserDetailsService 를 상속받습니다.
    // 토큰에 저장된 유저 정보를 활용할 수 있게 합니다.

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        return new UserDetailsImpl(user);
    }
}
