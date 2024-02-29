package pers.star.questionnaire.oss.service.impl;


import pers.star.questionnaire.oss.config.OSSConfig;
import pers.star.questionnaire.oss.constant.OSSConstants;
import pers.star.questionnaire.oss.exception.IllegalDatasourceProviderException;
import pers.star.questionnaire.oss.exception.NotFoundDatasourceException;
import pers.star.questionnaire.oss.pojo.TargetType;
import pers.star.questionnaire.oss.service.OSSOperation;
import pers.star.questionnaire.oss.service.OSSService;

import javax.annotation.Resource;

public class OSSServiceImpl implements OSSService {

    @Resource
    OSSConfig ossConfig;


    @Override
    public OSSOperation name(String name) throws NotFoundDatasourceException, IllegalDatasourceProviderException {
        return ossConfig.getDataSource(name);
    }

    @Override
    public OSSOperation default_() throws NotFoundDatasourceException, IllegalDatasourceProviderException {
        return ossConfig.getDataSource(OSSConstants.DEFAULT);
    }

    @Override
    public TargetType getTargetType(String name) {
        return ossConfig.getTargetType(name);
    }

}
