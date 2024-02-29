package pers.star.questionnaire.oss.exception;

public class IllegalDatasourceProviderException extends Throwable {
    public IllegalDatasourceProviderException(String provider) {
        super("oss:找不到服务提供商 " + provider);
    }
}
