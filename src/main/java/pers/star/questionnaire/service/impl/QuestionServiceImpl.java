package pers.star.questionnaire.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.star.questionnaire.mapper.QuestionMapper;
import pers.star.questionnaire.pojo.Question;
import pers.star.questionnaire.service.QuestionService;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
