package pers.star.questionnaire.oss.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 *  <a href="https://developer.qiniu.com/kodo/1206/put-policy">文档地址</a>
 */
@Data
@Builder
public class QiniuUploadPolicy {

    /**
     * 上传域
     */
    private String scope;

    /**
     * 允许用户上传以 scope 的 keyPrefix 为前缀的文件。
     */
    private int isPrefixalScope;

    /**
     * 过期时间
     */
    private long deadline;

    /**
     * 最大文件大小
     */

    private long fsizeLimit;

    /**
     * 开启 MimeType 侦测功能，并按照下述规则进行侦测；如不能侦测出正确的值，会默认使用 application/octet-stream 。
     * 设为非 0 值，则忽略上传端传递的文件 MimeType 信息，并按如下顺序侦测 MimeType 值：
     * 1. 侦测内容；
     * 2. 检查文件扩展名；
     * 3. 检查 Key 扩展名。
     * 默认设为 0 值，如上传端指定了 MimeType 则直接使用该值，否则按如下顺序侦测 MimeType 值：
     * 1. 检查文件扩展名；
     * 2. 检查 Key 扩展名；
     * 3. 侦测内容。
     */

    private int detectMime;
    /**
     * 限定用户上传的文件类型。指定本字段值，七牛服务器会侦测文件内容以判断 MimeType，再用判断值跟指定值进行匹配，匹配成功则允许上传，匹配失败则返回 403 状态码。示例：
     * image/* 表示只允许上传图片类型
     * image/jpeg;image/png 表示只允许上传 jpg 和 png 类型的图片
     * !application/json;text/plain 表示禁止上传 json 文本和纯文本。注意最前面的感叹号！
     */
    private String mimeLimit;
    /**
     * 文件存储类型。0 为标准存储（默认），1 为低频存储，2 为归档存储，3 为深度归档存储。
     */
    private int fileType;

    /**
     * 前端上传成功返回格式
     */

    private String returnBody;


    /**
     * 从抽象上传策略实例化
     * @param uploadPolicy 抽象上传策略
     * @return 七牛云上传策略
     */

    public static QiniuUploadPolicy fromCommonUploadPolicy(UploadPolicy uploadPolicy){
        QiniuUploadPolicyBuilder builder = new QiniuUploadPolicyBuilder()
                .deadline(uploadPolicy.getDeadline());

        if(StringUtils.hasLength(uploadPolicy.getMimeLimit())){
            builder.detectMime(1);
            builder.mimeLimit(uploadPolicy.getMimeLimit());
        }

        builder.deadline(uploadPolicy.getDeadline());
        builder.fileType(0);


        if(uploadPolicy.getMode() == UploadPolicy.MODE_RPEFIX){
            builder.isPrefixalScope(1);
        }

        builder.fsizeLimit(uploadPolicy.getFileMaxSize());
        builder.scope(uploadPolicy.getBucket() + ":" + uploadPolicy.getKeyPrefix());

        if(StringUtils.hasLength(uploadPolicy.getReturnBody())){
            builder.returnBody(uploadPolicy.getReturnBody());
        }

        return builder.build();
    }
}
