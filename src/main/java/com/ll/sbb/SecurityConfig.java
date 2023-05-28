package com.ll.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//스프링부트 환경설정 파일임을 의미하는 어노테이션
@EnableWebSecurity
//모든 요청 url이 스프링시큐리티의 제어를 받도록 만드는 어노테이션
@EnableMethodSecurity(prePostEnabled = true)
//메서드 수준의 접근 제어를 활성화하는 어노테이션
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()
                //모든 http 요청에 인가 없이 접근을 허락하는 코드
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                //X-Frame-Options 헤더는 웹 페이지를 iframe 내에서 로드하는 것을 제한하여 클릭재킹(clickjacking) 공격을 방지하는 데 사용
                //clickjacking : 웹 페이지에 클릭 오류를 유도해 공격하는 웹 페이지 공격 방법

                .and()
                .formLogin()
                //로그인하는데 사용하는 폼 기반 인증, 이 메서드를 통해 로그인페이지, 로그인 성공 후의 기본 url 지정
                .loginPage("/user/login")
                //로그인 페이지의 url 지정
                .defaultSuccessUrl("/")
                //로그인후에 이동할 기본 url
                .and()
                .logout()
                //로그아웃하는데 사용하는 폼 기반 인증, 이 메서드를 통해 로그아웃페이지, 로그아웃 성공 후의 기본 url 지정
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                //로그아웃 페이지의 url 지정
                .logoutSuccessUrl("/")
                //로그아웃후에 이동할 기본 url
                .invalidateHttpSession(true)
                //로그아웃시 로그인 세션 무효와
        ;

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //비밀번호 암호화
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    //AuthenticationManager : 사용자의 자격증명(이름, 비밀번호 등)

}
