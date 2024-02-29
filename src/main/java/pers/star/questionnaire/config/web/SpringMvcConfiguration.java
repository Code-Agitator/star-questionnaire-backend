package pers.star.questionnaire.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import pers.star.questionnaire.interceptor.BaseHandlerInterceptor;
import pers.star.questionnaire.util.LocalDateUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@ServletComponentScan
@Slf4j
public class SpringMvcConfiguration implements InitializingBean, WebMvcConfigurer {
    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    /**
     * 自定义的拦截器
     */
    @Resource
    private List<BaseHandlerInterceptor> baseHandlerInterceptors;


    /**
     * 用于 处理 CurrentUser注解的参数
     */
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        baseHandlerInterceptors.forEach(interceptor -> interceptor.register(registry));
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 我知道你的IDEA下面灰灰的你很难受 但是请不要动他 不然编译不过去
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return Optional.of(source).map(Long::valueOf).map(LocalDateUtil::of).orElse(null);
            }
        };
    }

    @Override
    public void afterPropertiesSet() {
        log.info("初始化并配置自定义的ResponseReturnValue处理器 - start");
        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>(Objects.requireNonNull(requestMappingHandlerAdapter.getReturnValueHandlers()));
        ResponseReturnValueHandler responseReturnValueHandler = new ResponseReturnValueHandler(messageConverters);
        int index = 0;
        for (int i = 0; i < returnValueHandlers.size(); i++) {
            if (RequestResponseBodyMethodProcessor.class.isAssignableFrom(returnValueHandlers.get(i).getClass())) {
                index = i;
                break;
            }
        }
        returnValueHandlers.add(index, responseReturnValueHandler);
        requestMappingHandlerAdapter.setReturnValueHandlers(returnValueHandlers);
        log.info("初始化并配置自定义的ResponseReturnValue处理器 - success");
    }


}
