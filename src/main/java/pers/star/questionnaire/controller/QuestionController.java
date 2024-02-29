package pers.star.questionnaire.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.star.questionnaire.advice.exception.children.ServerException;
import pers.star.questionnaire.domain.QComponent;
import pers.star.questionnaire.dto.Ids;
import pers.star.questionnaire.pojo.Question;
import pers.star.questionnaire.service.QuestionService;
import pers.star.questionnaire.util.MybatisUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @PostMapping("/api/question")
    public Map<String, Object> saveQuestion() {
        Question question = new Question();
        question.setTitle("新的问卷");
        question.setDesc("问卷的详细描述");
        question.setIsPublished(false);
        question.setComponentList(defaultComponentList());
        questionService.save(question);
        return Map.of("id", question.getId());

    }

    @GetMapping("/api/question")
    public Map<String, Object> findAllQuestion(String keyword, Boolean isStar, Boolean isDeleted, Integer pageSize, Integer page) {
        LambdaQueryChainWrapper<Question> query = questionService.lambdaQuery();
        query.like(StringUtils.isNotBlank(keyword), Question::getTitle, keyword);
        query.eq(Objects.nonNull(isStar), Question::getIsStar, isStar);
        query.eq(Question::getIsDeleted, ObjectUtils.defaultIfNull(isDeleted, false));
        Page<Question> list = query.page(MybatisUtils.initPage(page, pageSize));
        return Map.of(
                "list", list.getRecords(),
                "total", list.getTotal()
        );
    }

    @GetMapping("/api/question/{id}")
    public Question getQuestion(@PathVariable Integer id) {
        Question question = questionService.getById(id);
        if (Objects.isNull(question)) {
            throw new ServerException("问卷不存在");
        }
        return question;
    }

    @PostMapping("/api/question/duplicate/{id}")
    public Map<String, Object> duplicateQuestion(@PathVariable String id) {
        Question question = questionService.getById(id);
        if (Objects.isNull(question)) {
            throw new ServerException("不存在");
        }
        question.setId(null);
        question.setAnswerCount(0);
        questionService.save(question);
        return Map.of("id", question.getId());
    }

    @PatchMapping("/api/question/{id}")
    public void updateQuestion(@PathVariable Integer id, @RequestBody Question question) {
        question.setId(id);
        questionService.updateById(question);
    }

    @DeleteMapping("/api/question")
    public void deleteQuestion(@RequestBody Ids ids) {
        List<Integer> ids1 = ids.getIds();
        if (ObjectUtils.isNotEmpty(ids1)) {
            questionService.removeBatchByIds(ids1);
        }
    }

    private List<QComponent> defaultComponentList() {

        return List.of(
                QComponent.builder()
                        .feId("c1")
                        .type("questionInfo")
                        .title("问卷标题")
                        .isHidden(false)
                        .isLocked(false)
                        .props(Map.of(
                                "title", "问卷标题",
                                "desc", "问卷描述"
                        ))
                        .build(),
                QComponent.builder()
                        .feId("c2")
                        .type("questionTitle")
                        .title("标题")
                        .isHidden(false)
                        .isLocked(false)
                        .props(Map.of(
                                "text", "个人信息调研",
                                "level", 1,
                                "isCenter", false
                        ))
                        .build(),
                QComponent.builder()
                        .feId("c3")
                        .type("questionInput")
                        .title("输入框1")
                        .isHidden(false)
                        .isLocked(false)
                        .props(Map.of(
                                "title", "你的姓名",
                                "placeholder", "请输入姓名"
                        ))
                        .build()
        );
    }
}
