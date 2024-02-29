package pers.star.questionnaire.oss.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class UploadPolicy {


    /**
     * 存储桶
     */
    private String bucket;

    /**
     * key前缀
     */
    private String keyPrefix;


    /**
     * 凭证截至时间
     */
    private long deadline;

    /**
     * 上传成功服务端回调
     */
    private String callbackUrl;

    /**
     * 服务端回调host
     */
    private String callbackHost;

    /**
     * 前端上传成功返回值格式 目前仅支持七牛云
     * <a href="https://developer.qiniu.com/kodo/1206/put-policy">七牛云文档</a>
     */

    private String returnBody;

    /**
     * 文件最大大小
     */
    private long fileMaxSize;

    /**
     * 限制mime 默认:不限制
     */
    private String mimeLimit;


    /**
     * 文件存储类型。0 为标准存储（默认），1 为低频存储，2 为归档存储，3 为深度归档存储。
     */
    private int fileType;

    /**
     * 上传模式 默认是指定key前缀
     */
    private int mode = MODE_RPEFIX;


    public static final int MODE_RPEFIX = 1;


    public static UploadPolicy fromTargetType(TargetType targetType,String keyPrefix) {
        String returnBody = "{\"url\":\"https://" + targetType.getDataSource().getDownloadDomain() + "/$(key)\"}";
        return new UploadPolicy(targetType.getDataSource().getBucket(),
                keyPrefix,
                new Date().getTime() / 1000 + targetType.getExpired(),
                null,
                null,
                returnBody,
                targetType.getMaxFileSize(),
                targetType.getMimeType(),
                0, UploadPolicy.MODE_RPEFIX);
    }


}
