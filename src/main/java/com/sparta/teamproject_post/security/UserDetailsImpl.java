package com.sparta.teamproject_post.security;

import com.sparta.teamproject_post.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    // 스프링 시큐리티 UserDetails 객체로 권한 정보를 관리할 수 있게 해줍니다.

    private final User user;

    public UserDetailsImpl(User user){
        this.user =user;
    }

    public User getUser(){
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
