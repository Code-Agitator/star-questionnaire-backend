package pers.star.questionnaire.auth.service;

import cn.hutool.jwt.JWTException;
import pers.star.questionnaire.auth.pojo.TokenInfo;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;

public interface TokenService {
    String createToken(TokenUserInfo subject, long keepLiveTime);

    TokenUserInfo getUserInfo(String token) throws JWTException;

    TokenInfo distributeToken(TokenUserInfo subject);

    TokenInfo refreshToken(String token);
}
