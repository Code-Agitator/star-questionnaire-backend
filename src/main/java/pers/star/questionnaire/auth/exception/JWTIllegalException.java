package pers.star.questionnaire.auth.exception;

import cn.hutool.jwt.JWTException;

/**
 * jwt无效异常
 */
public class JWTIllegalException extends JWTException {
    public JWTIllegalException(Throwable e) {
        super(e);
    }

    public JWTIllegalException(String message) {
        super(message);
    }

    public JWTIllegalException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public JWTIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTIllegalException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
