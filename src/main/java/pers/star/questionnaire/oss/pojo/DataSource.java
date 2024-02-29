package pers.star.questionnaire.oss.pojo;

import lombok.Data;

@Data
public class DataSource {

    private String name;
    /**
     * 公钥
     */
    private String accessKey;
    /**
     * 私钥
     */
    private String secretKey;
    /**
     * 上传域名
     */
    private String uploadDomain;
    /**
     * <p>服务提供商</p>
     * <p>1.qiniu</p>
     * <p>2.aliyun</p>
     *
     */
    private String provider;

    private String bucket;

    private String downloadDomain;


}
