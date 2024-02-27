package pers.star.questionnaire.advice.exception.children;


import pers.star.questionnaire.advice.exception.WithMessageException;

/**
 * @author Agitator
 */
public class ServiceUnavailableException extends WithMessageException {
    public ServiceUnavailableException(String resultCode) {
        super(resultCode);
    }
}
