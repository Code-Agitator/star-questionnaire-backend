package pers.star.questionnaire.util;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;

public class AnnotationUtil {
    private AnnotationUtil() {

    }

    public static <T extends Annotation> T getFromHandlerMethodParam(Object handler, Class<T> clazz) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            for (MethodParameter methodParameter : methodParameters) {
                T annotation = methodParameter.getParameterAnnotation(clazz);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        return null;
    }


    public static <T extends Annotation> T getFromHandlerMethod(Object handler, Class<T> clazz) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethodAnnotation(clazz);
        }
        return null;
    }
}
