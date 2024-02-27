package pers.star.questionnaire.advice.exception.children;


import pers.star.questionnaire.advice.exception.WithMessageException;

/**
 * @author Agitator
 */
public class UnauthorizedException extends WithMessageException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
