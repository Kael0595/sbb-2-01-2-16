package com.ll.sbb.Question;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service//서비스 선언
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionrepository;

    public List<Question> getlist() {
        return this.questionrepository.findAll();
    }
    //questionrepository에서 찾은 question을 list 형태로 getlist에 저장

    public Question getquestion(Integer id) {
        Optional<Question> question = this.questionrepository.findById(id);
        //int 형태의 id를 받아 questionrepository에서 id에 맞는 question을 찾은 뒤 optional 형태의 question에 저장
        if (question.isPresent()) {
            //id값에 맞는 question이 존재하면
            return question.get();
            //question 출력
        }
        throw new DataNotFoundException("question not found");
        //question이 없으면 예외처리(datanotfoundexception)
    }

    public void create(String subject, String content) {
        Question question = new Question();
        //question 형태의 question 생성
        question.setSubject(subject);
        //question에 subject 저장
        question.setContent(content);
        //question에 content 저장
        question.setCreateDate(LocalDateTime.now());
        //question에 createdate 저장
        this.questionrepository.save(question);
        //questionrepository에 question 저장
    }
}
