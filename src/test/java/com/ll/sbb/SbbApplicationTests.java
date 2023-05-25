package com.ll.sbb;

import com.ll.sbb.Answer.AnswerRepository;
import com.ll.sbb.Question.Question;
import com.ll.sbb.Question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

    @Autowired//의존성 주입(해당 타입의 빈(Bean)을 자동으로 주입)
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
//    @Transactional
        //트랜잭션 내에서 모든 작업이 성공하면 커밋되고, 하나라도 실패하면 롤백
    void testJpa() {


//        Optional<question> oq = this.questionRepository.findById(2);
//        //questionRepository에서 id 2로 찾은 내용을 oq에 저장
//        assertTrue(oq.isPresent());
//        //oq에 내용이 있는지 검증
//        question q = oq.get();
//        //q에 oq값 저장
//        List<Answer> answerList = q.getAnswerList();
//        //answerList에 q의 answerList 저장
//        assertEquals(1, answerList.size());
//        //answerList에 저장된 answer 갯수가 1개인지 확인
//        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//        //answerList의 0번 인덱스에 저장된 내용이 "네 자동으로 생성됩니다."인지 확인
//        Optional<Answer> oa = this.answerRepository.findById(1);
//        //answerRepository에서 id 1로 찾은 내용을 oa에 저장
//        assertTrue(oa.isPresent());
//        //oa에 내용이 있는지 검증
//        Answer a = oa.get();
//        //a에 oa값 저장
//        assertEquals(2, a.getquestion().getId());
//        //answer이 2번 question에 저장되었는지 확인

//        Optional<question> oq = this.questionRepository.findById(2);
//        //questionRepository에서 id 2로 찾은 내용을 oq에 저장
//        assertTrue(oq.isPresent());
//        //oq에 내용이 있는지 검증
//        question q = oq.get();
//        //q에 oq값 저장
//
//        Answer a = new Answer();
//        //Answer 형태의 a 생성
//        a.setContent("네 자동으로 생성됩니다.");
//        //a의 content에 "네 자동으로 생성됩니다." 저장
//        a.setquestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
//        a.setCreateDate(LocalDateTime.now());
//        //a의 createDate에 현재시간 저장
//        this.answerRepository.save(a);
//        //answerRepository에 a저장

//        assertEquals(2, this.questionRepository.count());
//        //questionRepository에 저장된 갯수가 2개인지 확인
//        Optional<question> oq = this.questionRepository.findById(1);
//        //oq에 questionRepository에서 id 1로 찾은 question wjwkd
//        assertTrue(oq.isPresent());
//        //oq에 값이 존재하는지 검증
//        question q = oq.get();
//        //q에 oq 값을 저장
//        this.questionRepository.delete(q);
//        //questionRepository에서 q 삭제
//        assertEquals(1, this.questionRepository.count());
//        //questionRepository에 저장된 갯수가 1개인지 확인

        //        Optional<question> oq = this.questionRepository.findById(1);
//        //oq에 id 1로 찾은 question 저장
//        assertTrue(oq.isPresent());
//        //oq가 존재하는지 검증
//        question q = oq.get();
//        //q에 oq에 저장된 값 저장
//        q.setSubject("수정된 제목");
//        //q에 저장된 제목을 "수정된 제목"으로 변경
//        this.questionRepository.save(q);
//        //questionRepository에 q의 내용 저장

//        List<question> qList = this.questionRepository.findBySubjectLike("sbb%");
//        //qList에 questionRepository에서 sbb가 포함된 question을 찾아서 저장
//        question q = qList.get(0);
//        //q에 qList의 0번 인덱스에 있는 값 저장
//        assertEquals("sbb가 무엇인가요?", q.getSubject());
//        //q에 저장된 subject와 "sbb가 무엇인가요?"가 같은지 확인

//        question q = this.questionRepository.findBySubjectAndContent(
//                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//        //"sbb가 무엇인가요?"와 "sbb에 대해서 알고 싶습니다."로 찾은 값을 q에 저장
//        assertEquals(1, q.getId());
//        //q가 저장된 question id가 1인지 확인

        //        question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
//        //question 형태의 q에 questionRepository에서 "sbb가 무엇인가요?"로 찾은 question 저장
//        assertEquals(1, q.getId());
//        //q가 저장된 question id가 1인지 확인

//        Optional<question> oq = this.questionRepository.findById(1);
//        //Optional 형태의 oq에 questionRepository에 저장된 내용을 id로 찾은 내용 저장
//        //Optional<> : List<> 처럼 저장하는 방법의 일종, List<>는 null 저장 불가, Optional<>은 null 가능
//        if (oq.isPresent()) {
//        //oq의 값이 존재한다면
//            question q = oq.get();
//            //question 형태의 q에 oq값을 저장
//            assertEquals("sbb가 무엇인가요?", q.getSubject());
//            //q에 저장된 subject와 "sbb가 무엇인가요?"가 동일한지 확인
//        }

//        List<question> all = this.questionRepository.findAll();
//        //questionRepository에 findAll로 찾은 내용을 list 형태의 all에 저장
//        assertEquals(2, all.size());
//        //all에 들어있는 데이터가 2개인지 확인
//        question q = all.get(0);
//        //question 형태의 q에 all에서 가져온 데이터 저장
//        assertEquals("sbb가 무엇인가요?", q.getSubject());
//        //q에 저장된 제목과 "sbb가 무엇인가요?"가 동일한지 확인

        Question q1 = new Question();
        //question 형태의 q1 생성
        q1.setSubject("sbb가 무엇인가요?");
        //q1의 subject에 "sbb가 무엇인가요?" 저장
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        //q1의 content에 "sbb에 대해서 알고 싶습니다." 저장
        q1.setCreateDate(LocalDateTime.now());
        //q1의 createDate에 현재시간 저장
        this.questionRepository.save(q1);  // 첫번째 질문 저장
        //questionRepository에 q1에 저장했던 내용 저장

        Question q2 = new Question();
        //question 형태의 q2 생성
        q2.setSubject("스프링부트 모델 질문입니다.");
        //q2의 subject에 "스프링부트 모델 질문입니다." 저장
        q2.setContent("id는 자동으로 생성되나요?");
        //q2의 content에 "id는 자동으로 생성되나요?" 저장
        q2.setCreateDate(LocalDateTime.now());
        //q2의 createDate에 현재시간 저장
        this.questionRepository.save(q2);  // 두번째 질문 저장
        //questionRepository에 q2에 저장했던 내용 저장
    }
}
