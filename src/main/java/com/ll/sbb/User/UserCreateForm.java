package com.ll.sbb.User;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class UserCreateForm {

    @Size(min = 3, max = 25)
    //아이디 길이제한(최소 3글자, 최대 25글자)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    //공란시 필수항목입니다 출력
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}
