package pers.star.questionnaire.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import pers.star.questionnaire.domain.QComponent;

import java.util.List;

@Data
@TableName(autoResultMap = true)
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    @TableField("`desc`")
    private String desc;
    private String js;
    private String css;
    private Boolean isPublished;
    private Boolean isStar;
    private Boolean isDeleted;
    private Integer answerCount;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<QComponent> componentList;


}
