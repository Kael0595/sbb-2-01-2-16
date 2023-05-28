package com.ll.sbb.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    Optional<SiteUser> findByusername(String username);
    //username을 받아 siteuser에서 user 찾기


}
