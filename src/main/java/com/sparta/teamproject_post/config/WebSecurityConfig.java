package com.sparta.teamproject_post.config;

import com.sparta.teamproject_post.jwt.JwtAuthFilter;
import com.sparta.teamproject_post.jwt.Jwtutil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 합니다.
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final Jwtutil jwtutil;

    // 비밀번호를 암호화 해주는 코드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // h2-consol 사용 및 resources 접근 허용 설정
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // token을 사용하는 방식이기 때문에 csrf를 disable하게 설정합니다.
        http.csrf().disable();

        // 기본 설정인 session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정입니다.
        // STATELESS : 세선 사용 x
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 요청에 관한 사용권한 체크
        http.authorizeHttpRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 그 외 나머지 요청은 누구나 접근 가능
                .anyRequest().permitAll();

        // token 방식으로 인증 처리를 해야해서 disable 처리 했습니다.
        http.formLogin().disable();

        // rest api 만을 고려하여 기본 설정은 해제하였습니다.
        http.httpBasic().disable();

        // UsernamePasswordAuthenticationFilter 에 도달하기 전에 커스텀한 필터를 먼저 동작시킵니다.
        http.addFilterBefore(new JwtAuthFilter(jwtutil), UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }



}
