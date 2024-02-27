package pers.star.questionnaire.advice.exception.children;


import pers.star.questionnaire.advice.exception.WithMessageException;

/**
 * @author Agitator
 */
public class BadRequestException extends WithMessageException {
    public BadRequestException(String message) {
        super(message);
    }
}
