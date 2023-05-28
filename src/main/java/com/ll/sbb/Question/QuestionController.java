package com.ll.sbb.Question;

import com.ll.sbb.Answer.AnswerForm;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
//controller 선언
@RequiredArgsConstructor
//생성자 자동 생성 this값 가짐(ex this.questionRepository = questionRepository)
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        //url에서 page 가져오기 위한 매개변수, url에 page가 전달되지 않으면 default값으로 0이 나오게 설정
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

//    public Page<Question> getList(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        //페이지를 내림차순으로 정렬해서 저장하기 위한 리스트 생성
//        sorts.add(Sort.Order.desc("createDate"));
//        //페이지를 createDate 순으로 내림차순 정렬해 저장
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        //한페이지에 게시물 10개씩 내림차순 정렬
//        return this.questionRepository.findAll(pageable);
//    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<Question> questionList = this.questionService.getlist();
//        //questionList에 questionRepository에서 찾은 내용 저장
//        model.addAttribute("questionList", questionList);
//        //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
//        //addAttribute() : Model 객체에 속성과 값을 추가해, 해당 속성과 값을 뷰에서 사용할 수 있게 함.
//        //이를 통해 컨트롤러에서 생성된 데이터를 뷰로 전달하고, 뷰에서는 이 데이터를 활용하여 화면을 구성하거나 처리할 수 있음.
//        return "question_list";
//        //템플릿의 question_list 출력
////        List<question> questionList = this.questionRepository.findAll();
////        //questionList에 questionRepository에서 찾은 내용 저장
////        model.addAttribute("questionList", questionList);
////        //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
////        return "question_list";
////        //템플릿의 question_list 출력
//    }


    @GetMapping(value = "/detail/{id}")
    ///question/detail/{id} 주소로 맵핑
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        //{id}값을 파싱해서 int값으로 변경
        Question question = this.questionService.getquestion(id);
        //question에 id로 찾은 question 저장
        model.addAttribute("question", question);
        //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
        //addAttribute() : Model 객체에 속성과 값을 추가해, 해당 속성과 값을 뷰에서 사용할 수 있게 함.
        //이를 통해 컨트롤러에서 생성된 데이터를 뷰로 전달하고, 뷰에서는 이 데이터를 활용하여 화면을 구성하거나 처리할 수 있음.
        return "question_detail";
//    @GetMapping(value = "/question/detail/{id}")
//    ///question/detail/{id} 주소로 맵핑
//    public String detail(Model model, @PathVariable("id") Integer id) {
//    //{id}값을 파싱해서 int값으로 변경
//        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    //메서드 수준 또는 클래시 수준의 접근 제어를 설정하는 어노테이션
    //isAuthenticated() : 현재 사용자가 인증되었는지 확인(성공적으로 로그인했는지)
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
        ///create로 들어오면 question_form으로 돌아가기(게시글 작성 페이지)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        //인증된 사용자의 이름으로 유저 저장
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        //String 값으로 받은 subject, content, siteuser로 questionServie에서 question 생성
        return "redirect:/question/list";
        //생성 후 question/list로 돌아가기
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getquestion(id);
        //id값으로 question 찾아 question에 저장
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        //question의 subject를 questionForm의 subject에 저장
        questionForm.setContent(question.getContent());
        //question의 content를 questionForm의 content에 저장
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
            //유효성 검사에 에러 검출시 question_form으로 돌아가기
        }
        Question question = this.questionService.getquestion(id);
        //question에 id로 찾은 question 저장
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            //작성자id와 수정하려는 사람 id가 다르면
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            //수정권한이 없습니다 에러문구 출력
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        //받은 question, subject, content로 question 수정
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getquestion(id);
        //받은 id값으로 question 찾기
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            //작성자와 삭제하려는 사람이 다르면
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            //삭제권한이 없습니다 문구 출력
        }
        this.questionService.delete(question);
        //question 삭제
        return "redirect:/";
        //기본페이지로 돌아가기
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getquestion(id);
        //받은 id값으로 question 찾기
        SiteUser siteUser = this.userService.getUser(principal.getName());
        //추천인정보 siteuser에 저장
        this.questionService.vote(question, siteUser);
        //추천 저장
        return String.format("redirect:/question/detail/%s", id);
    }


//    @PostMapping("/create")
//    public String questionCreate(@RequestParam String subject, @RequestParam String content) {
//        this.questionService.create(subject, content);
//        //String 값으로 받은 subject, content로 questionServie에서 question 생성
//        return "redirect:/question/list";
//        //생성 후 question/list로 돌아가기
//    }

}
