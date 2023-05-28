package com.ll.sbb.Answer;

import com.ll.sbb.Question.Question;
import com.ll.sbb.Question.QuestionService;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               //url에서 id값을 받아 int형으로 변경
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        //bindingresult : 데이터 바인딩과 유효성 검사를 위한 인터페이스
        //Valid : 유효성 검사
        //principal : 인증된 사용자 정보 제공
        Question question = this.questionService.getquestion(id);
        //받은 id로 article을 받아 article에 저장
        SiteUser siteUser = this.userService.getUser(principal.getName());
        //인증된 사용자 이름 반환
        if (bindingResult.hasErrors()) {
            //유효성 검사에서 에러 나오면
            model.addAttribute("question", question);
            //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
            //addAttribute() : Model 객체에 속성과 값을 추가해, 해당 속성과 값을 뷰에서 사용할 수 있게 함.
            //이를 통해 컨트롤러에서 생성된 데이터를 뷰로 전달하고, 뷰에서는 이 데이터를 활용하여 화면을 구성하거나 처리할 수 있음.
            return "question_detail";
        }
        Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
        //답변 생성
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
//        this.answerService.create(question, answerForm.getContent(), siteUser);
//        //받은 값으로 답변 생성
//        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        //받은 id값으로 answer 찾기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            //작성자와 수정하려는 사람이 다르면
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            //수정권한이 없습니다 출력
        }
        answerForm.setContent(answer.getContent());
        //answer_form에 수정된 답변내용 저장
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            //유효성 검사에서 에러 검출시
            return "answer_form";
            //answer_form으로 돌아가기
        }
        Answer answer = this.answerService.getAnswer(id);
        //받은 id값으로 answer 찾기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        //받은 content로 answer 수정
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
//        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
//        //answer를 달아놓은 question의 id값에 맞는 question/detail로 돌아가기
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        //받은 id값으로 answer 찾기
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            //작성자와 삭제하려는 사람이 다르면
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            //삭제권한이 없습니다 문구 출력
        }
        this.answerService.delete(answer);
        //answer 삭제
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        //받은 id값으로 답변 찾기
        SiteUser siteUser = this.userService.getUser(principal.getName());
        //추천인 정보 siteuser에 저장
        this.answerService.vote(answer, siteUser);
        //answer 추천
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
//        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

//    @PostMapping("/create/{id}")
//    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
//        //@RequestParam String content : content라는 파라미터 값을 String 형태로 바인딩하는 어노테이션
//        Question question = this.questionService.getquestion(id);
//        //question에 받은 id를 통해서 questionservice에서 찾은 question 저장
//        return String.format("redirect:/question/detail/%s", id);
//    }

}
