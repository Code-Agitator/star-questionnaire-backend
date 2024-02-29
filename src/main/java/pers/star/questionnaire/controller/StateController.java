package pers.star.questionnaire.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.star.questionnaire.pojo.Answer;
import pers.star.questionnaire.service.AnswerService;
import pers.star.questionnaire.util.MybatisUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StateController {
    @Resource
    private AnswerService answerService;

    @GetMapping("/api/stat/{questionId}")
    public Map<String, Object> getState(@PathVariable Integer questionId, Integer pageSize, Integer page) {
        Page<Answer> result = answerService.lambdaQuery().eq(Answer::getQuestionId, questionId).page(MybatisUtils.initPage(page, pageSize));
        return Map.of(
                "list", result.getRecords(),
                "total", result.getTotal()
        );
    }

    @GetMapping("/api/stat/{questionId}/{feId}")
    public Map<String, Object> stateField(@PathVariable Integer questionId, @PathVariable String feId) {
        List<Answer> list = answerService.lambdaQuery().eq(Answer::getQuestionId, questionId).list();
        Map<String, Integer> counter = new HashMap<>();
        list.forEach(answer -> {
            Map<String, String> answers = answer.getAnswers();
            if (ObjectUtils.isEmpty(answers) || !answers.containsKey(feId)) {
                return;
            }
            String value = answers.get(feId);
            String[] items = value.split(",");
            for (String item : items) {
                if (StringUtils.isBlank(item)) {
                    continue;
                }
                counter.put(item, counter.getOrDefault(item, 0));
            }

        });
        return Map.of("stat",
                counter.entrySet().stream().map(entry -> Map.of(
                        "name", entry.getKey(),
                        "count", entry.getValue()
                )).collect(Collectors.toList())
        );
    }


}
