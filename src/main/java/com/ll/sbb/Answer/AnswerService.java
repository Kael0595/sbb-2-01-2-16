package com.ll.sbb.Answer;

import com.ll.sbb.Question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        //Answer 형태의 answer 생성
        answer.setContent(content);
        //answer에 content 저장
        answer.setCreateDate(LocalDateTime.now());
        //answer에 createDate 저장
        answer.setQuestion(question);
        //answer에 article 저장
        this.answerRepository.save(answer);
        //answerRepository에 answer 저장
    }

}
