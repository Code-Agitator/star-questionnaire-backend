package pers.star.questionnaire.interceptor;

import com.etw.application.advice.exception.children.UnauthorizedException;
import com.etw.application.anno.CurrentUser;
import com.etw.application.util.AnnotationUtil;
import com.etw.application.util.TokenUtil;
import com.etw.auth.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CurrentUserInterceptor extends BaseHandlerInterceptor {
    TokenService tokenService;

    public CurrentUserInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
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
