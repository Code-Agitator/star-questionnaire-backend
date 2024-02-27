package pers.star.questionnaire.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pers.star.questionnaire.anno.CurrentUser;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 当前用户注解的参数解析器, 用户将request中的当前用户信息注入到
 *
 * @author Agitator
 * @date 2022.07.24
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 被CurrentUser注解 并且参数类型为TokenUserInfo的时候注入
        return methodParameter.getParameterAnnotation(CurrentUser.class) != null && methodParameter.getParameterType() == TokenUserInfo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        return Optional.ofNullable(request.getAttribute(WebMvcConstant.USER_INFO_FIELD_IN_REQUEST)).orElse(TokenUserInfo.builder().build());
    }
}
