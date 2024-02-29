package pers.star.questionnaire.oss.exception;

public class NotFoundDatasourceException extends Exception{
    public NotFoundDatasourceException(String name) {
        super("无法获取oss数据源" + name);
    }
}
