package pers.star.questionnaire.oss.service;

import pers.star.questionnaire.oss.pojo.DataSource;
import pers.star.questionnaire.oss.pojo.UploadPolicy;

public interface OSSOperation {

    String getUploadTokenOfKeyPrefix(UploadPolicy uploadPolicy);

    DataSource getDatasource();


}
