package com.ll.sbb.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    //컬럼 속성 부여(유니크)
    private String username;

    private String password;
    @Column(unique = true)
    private String email;
}
