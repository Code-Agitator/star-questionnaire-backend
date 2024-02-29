package pers.star.questionnaire.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Agitator
 * @Description 封装响应体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "通用响应体")
public class JsonResult<T> implements Serializable {
    @Schema(description = "当前请求结果标识")
    private Boolean success;
    @Schema(description = "响应码")
    private Integer errno;
    @Schema(description = "错误信息")
    private String message;
    @Schema(description = "响应数据")
    private T data;

    /**
     * @param success 是否成功响应的bool值
     * @Description 带一个响应成功与否的构造函数
     */
    public JsonResult(boolean success) {
        this.success = success;
        this.errno = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.message = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
    }

    /**
     * @param success    是否响应成功
     * @param resultCode 响应状态信息
     * @Description 带有响应状态信息的构造函数
     */
    public JsonResult(boolean success, ResultCode resultCode) {
        this.success = success;
        this.errno = success ? ResultCode.SUCCESS.getCode() : (resultCode == null ? ResultCode.COMMON_FAIL.getCode() : resultCode.getCode());
        this.message = success ? ResultCode.SUCCESS.getMessage() : (resultCode == null ? ResultCode.COMMON_FAIL.getMessage() : resultCode.getMessage());
    }

    /**
     * @param success 是否成功响应
     * @param data    携带数据
     * @Description 携带数据的构造函数
     */
    public JsonResult(boolean success, T data) {
        this.success = success;
        this.errno = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.message = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    /**
     * @param success    是否成功响应
     * @param resultCode 自选响应转态码
     * @param data       携带数据
     * @Description 携带数据以及自选响应码的构造函数
     */
    public JsonResult(boolean success, ResultCode resultCode, T data) {
        this.success = success;
        this.errno = success ? ResultCode.SUCCESS.getCode() : (resultCode == null ? ResultCode.COMMON_FAIL.getCode() : resultCode.getCode());
        this.message = success ? ResultCode.SUCCESS.getMessage() : (resultCode == null ? ResultCode.COMMON_FAIL.getMessage() : resultCode.getMessage());
        this.data = data;
    }

    /**
     * @param success    是否成功响应
     * @param resultCode 自选响应转态码
     * @param message    自定义消息
     * @Description 携带数据以及自选响应码的构造函数
     */
    public JsonResult(boolean success, ResultCode resultCode, String message) {
        this.success = success;
        this.errno = resultCode.getCode();
        this.message = message;
    }

    /**
     * @param resultCode 自选响应转态码
     * @param data       携带数据
     * @Description 用户响应Socket 不需要携带success
     */
    public JsonResult(ResultCode resultCode, T data) {
        this.success = true;
        this.errno = resultCode == null ? ResultCode.COMMON_FAIL.getCode() : resultCode.getCode();
        this.message = resultCode == null ? ResultCode.COMMON_FAIL.getMessage() : resultCode.getMessage();
        this.data = data;
    }


}
