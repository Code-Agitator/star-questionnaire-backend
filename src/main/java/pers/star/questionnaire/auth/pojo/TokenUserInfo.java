package pers.star.questionnaire.auth.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenUserInfo {
    /**
     *
     */
    private Integer id;

    private String username;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 上一次登录时间
     */
    private Long lastLoginTime;


    /**
     * 微信openid
     */
    private String wxOpenid;

    @Override
    public String toString() {
        return username;
    }
}
