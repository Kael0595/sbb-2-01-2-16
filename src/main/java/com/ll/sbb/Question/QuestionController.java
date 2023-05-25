package com.ll.sbb.Question;

import com.ll.sbb.Answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//controller 선언
@RequiredArgsConstructor
//생성자 자동 생성 this값 가짐(ex this.questionRepository = questionRepository)
@RequestMapping("/question")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getlist();
        //questionList에 questionRepository에서 찾은 내용 저장
        model.addAttribute("questionList", questionList);
        //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
        //addAttribute() : Model 객체에 속성과 값을 추가해, 해당 속성과 값을 뷰에서 사용할 수 있게 함.
        //이를 통해 컨트롤러에서 생성된 데이터를 뷰로 전달하고, 뷰에서는 이 데이터를 활용하여 화면을 구성하거나 처리할 수 있음.
        return "question_list";
        //템플릿의 question_list 출력
//        List<question> questionList = this.questionRepository.findAll();
//        //questionList에 questionRepository에서 찾은 내용 저장
//        model.addAttribute("questionList", questionList);
//        //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
//        return "question_list";
//        //템플릿의 question_list 출력
    }

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

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
        ///create로 들어오면 question_form으로 돌아가기(게시글 작성 페이지)
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        //String 값으로 받은 subject, content로 questionServie에서 question 생성
        return "redirect:/question/list";
        //생성 후 question/list로 돌아가기
    }


//    @PostMapping("/create")
//    public String questionCreate(@RequestParam String subject, @RequestParam String content) {
//        this.questionService.create(subject, content);
//        //String 값으로 받은 subject, content로 questionServie에서 question 생성
//        return "redirect:/question/list";
//        //생성 후 question/list로 돌아가기
//    }

}
