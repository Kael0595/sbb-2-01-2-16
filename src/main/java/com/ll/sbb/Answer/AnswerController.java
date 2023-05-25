package com.ll.sbb.Answer;

import com.ll.sbb.Question.Question;
import com.ll.sbb.Question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               //url에서 id값을 받아 int형으로 변경
                               @Valid AnswerForm answerForm, BindingResult bindingResult) {
        //bindingresult : 데이터 바인딩과 유효성 검사를 위한 인터페이스
        //Valid : 유효성 검사
        Question question = this.questionService.getquestion(id);
        //받은 id로 article을 받아 article에 저장
        if (bindingResult.hasErrors()) {
            //유효성 검사에서 에러 나오면
            model.addAttribute("question", question);
            //model : 뷰 템플릿에 접근하여 표시하거나 처리할 수 있게 하는 파라미터
            //addAttribute() : Model 객체에 속성과 값을 추가해, 해당 속성과 값을 뷰에서 사용할 수 있게 함.
            //이를 통해 컨트롤러에서 생성된 데이터를 뷰로 전달하고, 뷰에서는 이 데이터를 활용하여 화면을 구성하거나 처리할 수 있음.
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent());
        //받은 값으로 답변 생성
        return String.format("redirect:/question/detail/%s", id);
    }

//    @PostMapping("/create/{id}")
//    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
//        //@RequestParam String content : content라는 파라미터 값을 String 형태로 바인딩하는 어노테이션
//        Article article = this.articleService.getArticle(id);
//        //article에 받은 id를 통해서 articleservice에서 찾은 article 저장
//        return String.format("redirect:/question/detail/%s", id);
//    }

}
