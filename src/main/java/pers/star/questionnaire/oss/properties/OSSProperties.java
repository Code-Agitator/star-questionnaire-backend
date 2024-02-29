package pers.star.questionnaire.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pers.star.questionnaire.oss.pojo.DataSource;
import pers.star.questionnaire.oss.pojo.TargetType;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {
    /**
     * 上传目标源
     */
    List<DataSource> datasources;
    /**
     * 目标类型
     */

    List<TargetType> targetType;

}
