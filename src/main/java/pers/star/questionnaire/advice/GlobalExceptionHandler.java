package pers.star.questionnaire.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.star.questionnaire.common.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;


/**
 * 控制器增强
 *
 * @author Agitator
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ErrorMessage runtimeExceptionHandler(RuntimeException exception, HttpServletRequest request) {
        logUnknownException(exception, request);
        return new ErrorMessage(exception.getMessage());
    }


    @SneakyThrows
    private void logUnknownException(Exception ex, HttpServletRequest request) {
        String traceId = IdUtil.fastSimpleUUID();
        String message = getExceptionMessage(ex);
        String body = IoUtil.read(request.getInputStream(), StandardCharsets.UTF_8);
        log.error("[{}] {} =>track:[{}] request body:{} ,exception:{}", request.getMethod(), request.getRequestURI(), traceId, message, body, ex);
    }

    private void logException(Exception ex, HttpServletRequest request) {
        String traceId = IdUtil.fastSimpleUUID();
        String message = getExceptionMessage(ex);
        String throwAt = getThrowAt(ex);
        log.error("[{}] {} => at:[{}] track:[{}] {}", request.getMethod(), request.getRequestURI(), throwAt, traceId, message);
    }

    private String getThrowAt(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (ObjectUtils.isEmpty(stackTrace)) {
            return "";
        }
        StackTraceElement stackTraceElement = stackTrace[0];
        return stackTraceElement.getClassName() + ":" + stackTraceElement.getLineNumber();
    }

    private String getExceptionMessage(Exception exception) {
        StringBuilder message = new StringBuilder();
        Throwable ex = ExceptionUtils.getRootCause(exception);
        message.append(ex.getClass().getSimpleName()).append(":").append(ex.getMessage());
        return message.toString();
    }

}
