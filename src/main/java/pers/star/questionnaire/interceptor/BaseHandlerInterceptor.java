package pers.star.questionnaire.interceptor;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

public abstract class BaseHandlerInterceptor implements HandlerInterceptor, Ordered {

    public void register(InterceptorRegistry registry) {
        registry.addInterceptor(this).order(getOrder());
    }
}
