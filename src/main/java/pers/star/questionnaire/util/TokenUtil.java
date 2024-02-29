package pers.star.questionnaire.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.star.questionnaire.advice.exception.children.ServerException;
import pers.star.questionnaire.advice.exception.children.UnauthorizedException;
import pers.star.questionnaire.auth.constant.TokenConstant;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.config.web.WebMvcConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class TokenUtil {
    private TokenUtil() {

    }

    public static TokenUserInfo getOrSetTokenUserInfo(HttpServletRequest request, TokenService tokenService) throws UnauthorizedException {
        final Object userInfoObject = request.getAttribute(WebMvcConstant.USER_INFO_FIELD_IN_REQUEST);
        if (userInfoObject != null) {
            return (TokenUserInfo) userInfoObject;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new ServerException("未登录");
        }
        Object principal = authentication.getPrincipal();
        request.setAttribute(WebMvcConstant.USER_INFO_FIELD_IN_REQUEST, (TokenUserInfo) principal);
        return (TokenUserInfo) principal;


    }


    private static String getToken(HttpServletRequest request) {
        return request.getHeader(TokenConstant.TOKEN_FIELD);
    }
}
