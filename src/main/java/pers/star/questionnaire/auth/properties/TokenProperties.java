package pers.star.questionnaire.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token 的一些配置
 */
@ConfigurationProperties(prefix = "etw.token")
@Data
public class TokenProperties {
    /**
     * 密钥
     */
    String key = "etw123456";
    /**
     * token保持时间 单位ms
     */
    long keepLiveTime = 1000 * 60 * 60 * 24;
    /**
     * refresh保持时间 单位ms
     */
    long keepRefreshLiveTime = 1000 * 60 * 60 * 24 * 3;
    /**
     * 签发人
     */
    String issuer = "etw";
    /**
     * 校验时效性时宽限范围
     */
    int leeway = 1000;
}
