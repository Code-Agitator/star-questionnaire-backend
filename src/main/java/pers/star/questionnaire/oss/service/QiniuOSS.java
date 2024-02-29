package pers.star.questionnaire.oss.service;


import pers.star.questionnaire.oss.pojo.QiniuUploadPolicy;

public interface QiniuOSS extends OSSOperation {

    String getUploadTokenOfKeyPrefix(QiniuUploadPolicy uploadPolicy);
}
