package com.ll.sbb.Question;

import com.ll.sbb.Answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    //id 속성 부여
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //primary key(고유키) 지정, auto_increment(자동으로 값 증가) 부여
    private Integer id;

    @Column(length = 200)
    //column 속성 부여, 글자수 200개 제한
    private String subject;

    @Column(columnDefinition = "TEXT")
    //column 속성 부여, String으로 받은 내용을 db에 text 형식으로 변경해 저장
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //1개의 게시물에 여러개 답변 달 수 있음, 참조내용 : question, question을 삭제하면 answer도 삭제
    private List<Answer> answerList;
    //List 형태로 answer List 생성
}
