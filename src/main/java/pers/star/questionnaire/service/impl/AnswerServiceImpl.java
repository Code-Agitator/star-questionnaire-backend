package pers.star.questionnaire.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.star.questionnaire.mapper.AnswerMapper;
import pers.star.questionnaire.pojo.Answer;
import pers.star.questionnaire.service.AnswerService;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
}
