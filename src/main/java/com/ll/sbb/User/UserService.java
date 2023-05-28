package com.ll.sbb.User;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        //SiteUser 속성의 user 생성
        user.setUsername(username);
        //받은 username를 user에 저장
        user.setEmail(email);
        //받은 email을 user에 저장
        user.setPassword(passwordEncoder.encode(password));
        //passwordEncoder로 암호화된 비밀번호 user에 저장
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        //비밀번호 암호화
//        user.setPassword(passwordEncoder.encode(password));
//        //받은 password를 user에 저장
        this.userRepository.save(user);
        //user 내용을 userRepository에 저장
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        //받은 username로 userRepository에서 유저 찾기
        if (siteUser.isPresent()) {
            //유저가 있으면
            return siteUser.get();
            //유저 가져오기
        } else {
            throw new DataNotFoundException("siteuser not found");
            //유저를 못찾으면 siteuser not found 문구 출력
        }
    }

}
