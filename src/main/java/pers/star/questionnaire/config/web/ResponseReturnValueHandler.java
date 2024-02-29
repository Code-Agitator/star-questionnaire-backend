package pers.star.questionnaire.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import pers.star.questionnaire.anno.CommonIgnore;
import pers.star.questionnaire.common.ResultTool;

import java.io.IOException;
import java.util.List;

/**
 * 接口返回值包装处理器
 *
 * @author llz
 * @date 2022.07.20
 */
@Component
public class ResponseReturnValueHandler extends RequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {
    public ResponseReturnValueHandler(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        CommonIgnore commonIgnoreOnMethod = returnType.getMethodAnnotation(CommonIgnore.class);
        CommonIgnore commonIgnoreOnClass = returnType.getContainingClass().getAnnotation(CommonIgnore.class);
        if (commonIgnoreOnMethod != null) {
            return !commonIgnoreOnMethod.ignoreReturnValueHandler();
        }
        if (commonIgnoreOnClass != null) {
            return !commonIgnoreOnClass.ignoreReturnValueHandler();
        }
        return super.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException, IOException {
        if (notSwaggerApi((ServletWebRequest) webRequest)) {
            returnValue = ResultTool.success(returnValue);
        }
        super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    private static boolean notSwaggerApi(ServletWebRequest webRequest) {
        return !webRequest.getRequest().getRequestURI().contains("v3/api-docs");
    }


}
