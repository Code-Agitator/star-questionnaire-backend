package pers.star.questionnaire.interceptor;

import com.etw.application.advice.exception.children.BadRequestException;
import com.etw.application.anno.MsgCheck;
import com.etw.application.config.web.EtwRequestWrapper;
import com.etw.application.service.common.WxApiService;
import com.etw.application.util.AnnotationUtil;
import com.etw.common.result.ResultCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Component
public class MsgCheckInterceptor extends BaseHandlerInterceptor {
    @Resource
    WxApiService wxApiService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        EtwRequestWrapper requestWrapper = (request instanceof EtwRequestWrapper) ? (EtwRequestWrapper) request : new EtwRequestWrapper(request);
        final MsgCheck msgCheck = AnnotationUtil.getFromHandlerMethod(handler, MsgCheck.class);
        if (msgCheck != null) {
            final String body = requestWrapper.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (!wxApiService.msgSecCheck(body)) {
                throw new BadRequestException(ResultCode.CONTENT_SENSITIVE);
            }
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
