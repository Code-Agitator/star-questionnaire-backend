package pers.star.questionnaire.oss.service;

import pers.star.questionnaire.oss.exception.IllegalDatasourceProviderException;
import pers.star.questionnaire.oss.exception.NotFoundDatasourceException;
import pers.star.questionnaire.oss.pojo.TargetType;

;

public interface OSSService {
    OSSOperation name(String name) throws NotFoundDatasourceException, IllegalDatasourceProviderException;

    OSSOperation default_() throws NotFoundDatasourceException, IllegalDatasourceProviderException;

    TargetType getTargetType(String name);

}
