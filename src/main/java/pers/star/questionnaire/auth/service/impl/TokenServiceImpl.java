package pers.star.questionnaire.auth.service.impl;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.RegisteredPayload;
import pers.star.questionnaire.auth.constant.TokenConstant;
import pers.star.questionnaire.auth.exception.JWTExpiredException;
import pers.star.questionnaire.auth.exception.JWTIllegalException;
import pers.star.questionnaire.auth.pojo.TokenInfo;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.auth.properties.TokenProperties;
import pers.star.questionnaire.auth.service.TokenService;

import java.nio.charset.StandardCharsets;
import java.util.Date;


public class TokenServiceImpl implements TokenService {
    TokenProperties tokenProperties;

    public TokenServiceImpl(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public String createToken(TokenUserInfo userInfo, long keepLiveTime) {
        Date now = new Date();
        return JWT.create()
                .setPayload(TokenConstant.TokenSubjectField.USER_INFO, userInfo)
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiresAt(new Date(now.getTime() + keepLiveTime))
                .setKey(tokenProperties.getKey().getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    @Override
    public TokenUserInfo getUserInfo(String token) throws JWTException {
        try {
            JWT jwt = JWT.of(token).setKey(tokenProperties.getKey().getBytes(StandardCharsets.UTF_8));
            if (!jwt.verify()) {
                throw new JWTIllegalException("current token is illegal");
            }
            if (!jwt.validate(tokenProperties.getLeeway())) {
                throw new JWTExpiredException("current token is already expired");
            }
            return ((JSONObject) jwt.getPayload(TokenConstant.TokenSubjectField.USER_INFO)).toBean(TokenUserInfo.class);
        } catch (JSONException e) {
            throw new JWTIllegalException("current token is illegal");
        }

    }

    @Override
    public TokenInfo distributeToken(TokenUserInfo subject) {
        Date now = new Date();
        return TokenInfo.builder()
                .expireIn(now.getTime() + tokenProperties.getKeepLiveTime())
                .refreshTokenExpireIn(now.getTime() + tokenProperties.getKeepRefreshLiveTime())
                .token(createToken(subject, tokenProperties.getKeepLiveTime()))
                .refreshToken(createToken(subject, tokenProperties.getKeepRefreshLiveTime())).build();
    }

    @Override
    public TokenInfo refreshToken(String refreshToken) {
        try {
            JWT jwt = JWT.of(refreshToken).setKey(tokenProperties.getKey().getBytes(StandardCharsets.UTF_8));
            Date expiresAt = new Date((Integer) jwt.getPayload(RegisteredPayload.EXPIRES_AT) * 1000L);
            if (!jwt.verify()) {
                throw new JWTIllegalException("current refresh token is illegal");
            }
            if (!jwt.validate(tokenProperties.getLeeway())) {
                throw new JWTExpiredException("current refresh token is already expired");
            }
            return TokenInfo.builder()
                    .expireIn(System.currentTimeMillis() + tokenProperties.getKeepLiveTime())
                    .refreshTokenExpireIn(expiresAt.getTime())
                    .token(createToken(((JSONObject) jwt.getPayload(TokenConstant.TokenSubjectField.USER_INFO)).toBean(TokenUserInfo.class), tokenProperties.getKeepLiveTime()))
                    .refreshToken(refreshToken).build();
        } catch (JSONException e) {
            throw new JWTIllegalException("current refresh token is illegal");
        }
    }


}
