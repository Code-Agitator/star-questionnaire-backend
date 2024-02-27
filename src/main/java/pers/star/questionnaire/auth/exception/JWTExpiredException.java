package pers.star.questionnaire.auth.exception;

import cn.hutool.jwt.JWTException;

/**
 * jwt过期异常
 */
public class JWTExpiredException extends JWTException {
    public JWTExpiredException(Throwable e) {
        super(e);
    }

    public JWTExpiredException(String message) {
        super(message);
    }

    public JWTExpiredException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public JWTExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTExpiredException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
