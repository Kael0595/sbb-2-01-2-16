package com.ll.sbb.Question;

import com.ll.sbb.Answer.Answer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service//서비스 선언
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);
//        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

//    public List<Question> getlist() {
//        return this.questionRepository.findAll();
//    }
//    //questionrepository에서 찾은 question을 list 형태로 getlist에 저장

    public Question getquestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        //int 형태의 id를 받아 questionRepository에서 id에 맞는 question을 찾은 뒤 optional 형태의 question에 저장
        if (question.isPresent()) {
            //id값에 맞는 question이 존재하면
            return question.get();
            //question 출력
        }
        throw new DataNotFoundException("question not found");
        //question이 없으면 예외처리(datanotfoundexception)
    }

    public void create(String subject, String content, SiteUser user) {
        Question question = new Question();
        //question 형태의 question 생성
        question.setSubject(subject);
        //question에 subject 저장
        question.setContent(content);
        //question에 content 저장
        question.setCreateDate(LocalDateTime.now());
        //question에 createdate 저장
        question.setAuthor(user);
        //question에 user 저장
        this.questionRepository.save(question);
        //questionrepository에 question 저장
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        //question에 받은 subject 저장
        question.setContent(content);
        //question에 받은 content 저장
        question.setModifyDate(LocalDateTime.now());
        //question에 수정일자(현재시간) 저장
        this.questionRepository.save(question);
        //questionRepository에 question 저장
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
        //questionRepository에서 question 삭제
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        //question에 추천 추가
        this.questionRepository.save(question);
        //questionRepository에 추천 저장
    }

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                //u1에 question을 기준으로 siteuser를 left outer join(q에 대해 author을 사용해서)한 내용 저장
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                //a에 question을 기준으로 answer를 left outer join(q에 대해 answerList를 사용해서)한 내용 저장
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                //u2에 answer를 기준으로 siteuser를 left outer join(a에 대해 author을 사용해서)한 내용 저장
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

}
