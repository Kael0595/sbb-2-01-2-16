package com.ll.sbb.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    //Answer, Integer를 매개변수로 받는 JpaRepository를 상속받아 만든 인터페이스 형태의 AnswerRepository
}
