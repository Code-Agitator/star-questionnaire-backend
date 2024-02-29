package pers.star.questionnaire.oss.service.impl;

import com.qiniu.util.Auth;
import pers.star.questionnaire.oss.pojo.DataSource;
import pers.star.questionnaire.oss.pojo.QiniuUploadPolicy;
import pers.star.questionnaire.oss.pojo.UploadPolicy;
import pers.star.questionnaire.oss.service.QiniuOSS;

public class QiniuOSSImpl implements QiniuOSS {

    private final Auth auth;
    private final DataSource dataSource;

    public QiniuOSSImpl(DataSource dataSource) {
        auth = Auth.create(dataSource.getAccessKey(), dataSource.getSecretKey());
        this.dataSource = dataSource;
    }

    @Override
    public String getUploadTokenOfKeyPrefix(UploadPolicy uploadPolicy) {
        return auth.uploadTokenWithPolicy(QiniuUploadPolicy.fromCommonUploadPolicy(uploadPolicy));
    }

    @Override
    public DataSource getDatasource() {
        return dataSource;
    }

    @Override
    public String getUploadTokenOfKeyPrefix(QiniuUploadPolicy uploadPolicy) {
        return null;
    }
}
