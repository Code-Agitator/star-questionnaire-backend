package pers.star.questionnaire.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.star.questionnaire.anno.CurrentUser;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.pojo.Answer;
import pers.star.questionnaire.service.AnswerService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Resource
    private AnswerService answerService;

    @PostMapping("/answer")
    public void saveAnswer(@RequestBody Answer answer, @CurrentUser TokenUserInfo tokenUserInfo) {
        answer.setUserId(tokenUserInfo.getId());
        answerService.save(answer);
    }

}
