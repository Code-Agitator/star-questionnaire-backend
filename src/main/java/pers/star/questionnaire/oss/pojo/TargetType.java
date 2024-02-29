package pers.star.questionnaire.oss.pojo;

import lombok.Data;

/**
 * 上传目标类型
 */
@Data
public class TargetType {
    private String name;

    private long maxFileSize;

    private long expired;

    private String mimeType;

    private String datasourceName;

    private DataSource dataSource;

    private String returnBody;

    private String keyPrefix;

}
