package com.ll.sbb.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //question, Integer를 매개변수로 받는 JpaRepository를 상속받아 만든 인터페이스 형태의 questionRepository

    Question findBySubject(String subject);
    //받은 subject값으로 question 찾기

    Question findBySubjectAndContent(String subject, String content);
    //받은 subject와 content로 question 찾기

    List<Question> findBySubjectLike(String subject);
    //받은 subject값으로 question 찾기

    Page<Question> findAll(Pageable pageable);
    //받은 pageable로 question 페이지수 찾기

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    //Specification<Question> spec: Question 엔티티의 조회 조건을 지정하기 위한 Specification 객체
    //Specification : 일반적으로 조회 조건을 표현하기 위해 사용되는 클래스
    //Pageable pageable: 조회 결과를 페이지 단위로 반환하기 위한 Pageable 객체
    //페이지 번호, 크기, 정렬방식 등을 통해 데이터를 페이징하는데 사용
    //page<Question> : 조회된 Question 엔티티들을 페이지 단위로 포함하는 page 객체 반환
    //조회 결과에 대한 페이징 정보와 함께 실제 데이터 포함

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
