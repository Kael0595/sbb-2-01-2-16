package com.ll.sbb.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        //username으로 siteuser에 저장된 유저 찾기
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
            //유저를 못 찾을경우 예외처리
        }
        SiteUser siteUser = _siteUser.get();
        //siteuser에 찾은 user 정보 저장
        List<GrantedAuthority> authorities = new ArrayList<>();
        //GrantedAuthority : 인증된 사용자 권한을 나타내는 인터페이스
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            //찾은 유저가 admin인 경우 admin 권한 부여
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
            //찾은 유저가 user인 경우 user 권한 부여
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
        //유저 이름과 비밀번호, 권한으로 유저 생성
    }
}