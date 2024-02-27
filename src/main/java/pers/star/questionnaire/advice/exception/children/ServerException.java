package pers.star.questionnaire.advice.exception.children;


import pers.star.questionnaire.advice.exception.WithMessageException;

/**
 * 通用异常处理
 */
public class ServerException extends WithMessageException {

    public ServerException(String message) {
        super(message);
    }


}
