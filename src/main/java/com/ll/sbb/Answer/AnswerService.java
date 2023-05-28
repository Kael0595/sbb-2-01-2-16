package com.ll.sbb.Answer;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.Question.Question;
import com.ll.sbb.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        //Answer 형태의 answer 생성
        answer.setContent(content);
        //answer에 content 저장
        answer.setCreateDate(LocalDateTime.now());
        //answer에 createDate 저장
        answer.setQuestion(question);
        //answer에 question 저장
        answer.setAuthor(siteUser);
        //answer에 user정보 저장
        this.answerRepository.save(answer);
        //answerRepository에 answer 저장
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        //받은 id값으로 답변 찾아서 Optional리스트에 저장
        if (answer.isPresent()) {
            //id값에 해당하는 answer 있을경우
            return answer.get();
            //answer 가져오기
        } else {
            throw new DataNotFoundException("answer not found");
            //없으면 answer not found 출력
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        //받은 content answer에 저장
        answer.setModifyDate(LocalDateTime.now());
        //수정일자에 현재시간 저장
        this.answerRepository.save(answer);
        //변경한 answer를 answerRepository에 저장
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
        //answerRepository에서 answer 삭제
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        //answer에 추천 추가
        this.answerRepository.save(answer);
        //answerRepository에 answer 저장
    }

}
