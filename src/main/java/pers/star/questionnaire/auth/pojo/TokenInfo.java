package pers.star.questionnaire.auth.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 返回到前端的token数据
 */
@Data
@Builder
public class TokenInfo {
    /**
     * 访问 token
     */
    String accessToken;
    /**
     * 刷新 token
     */
    String refreshToken;
    /**
     * 访问 token 有效时间
     */
    long expireIn;
    /**
     * 刷新 token 有效时间
     */
    long refreshTokenExpireIn;
}
