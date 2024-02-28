package pers.star.questionnaire.interceptor;

import org.springframework.stereotype.Component;
import pers.star.questionnaire.advice.exception.children.UnauthorizedException;
import pers.star.questionnaire.anno.CurrentUser;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.util.AnnotationUtil;
import pers.star.questionnaire.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CurrentUserInterceptor extends BaseHandlerInterceptor {
    TokenService tokenService;

    public CurrentUserInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CurrentUser annotation = AnnotationUtil.getFromHandlerMethodParam(handler, CurrentUser.class);
        if (annotation == null) {
            return true;
        }
        try {
            TokenUtil.getOrSetTokenUserInfo(request, tokenService);
        } catch (UnauthorizedException e) {
            if (annotation.require()) {
                throw e;
            }
        }
        return true;
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
