package com.ll.sbb.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //question, Integer를 매개변수로 받는 JpaRepository를 상속받아 만든 인터페이스 형태의 questionRepository

    Question findBySubject(String subject);
    //받은 subject값으로 question 찾기

    Question findBySubjectAndContent(String subject, String content);
    //받은 subject와 content로 question 찾기

    List<Question> findBySubjectLike(String subject);
    //받은 subject값으로 question 찾기


}
