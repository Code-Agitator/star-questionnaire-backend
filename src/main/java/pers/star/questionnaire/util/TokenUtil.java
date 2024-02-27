package pers.star.questionnaire.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.JWTException;
import pers.star.questionnaire.advice.exception.children.UnauthorizedException;
import pers.star.questionnaire.auth.constant.TokenConstant;
import pers.star.questionnaire.auth.exception.JWTExpiredException;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.config.web.WebMvcConstant;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    private TokenUtil() {

    }

    public static TokenUserInfo getOrSetTokenUserInfo(HttpServletRequest request, TokenService tokenService) throws UnauthorizedException {
        final Object userInfoObject = request.getAttribute(WebMvcConstant.USER_INFO_FIELD_IN_REQUEST);
        if (userInfoObject != null) {
            return (TokenUserInfo) userInfoObject;
        }
        final String tokenStr = getToken(request);
        if (CharSequenceUtil.isBlank(tokenStr)) {
            throw new UnauthorizedException(ResultCode.USER_NOT_LOGIN);
        }

        try {
            TokenUserInfo userInfo = tokenService.getUserInfo(tokenStr);
            if (userInfo == null || userInfo.getId() == null) {
                throw new UnauthorizedException(ResultCode.USER_NOT_LOGIN);
            }
            request.setAttribute(WebMvcConstant.USER_INFO_FIELD_IN_REQUEST, userInfo);
            return userInfo;
        } catch (JWTException e) {
            if (e instanceof JWTExpiredException) {
                throw new UnauthorizedException(ResultCode.USER_TOKEN_EXPIRES);
            } else {
                throw new UnauthorizedException(ResultCode.USER_TOKEN_ILLEGAL);
            }
        }

    }


    private static String getToken(HttpServletRequest request) {
        return request.getHeader(TokenConstant.TOKEN_FIELD);
    }
}
