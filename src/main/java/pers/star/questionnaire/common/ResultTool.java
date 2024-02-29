package pers.star.questionnaire.common;

/**
 * @author Agitator
 * @description 返回体构造工具
 */
public class ResultTool {
    private ResultTool(){

    }
    public static JsonResult<Void> success() {
        return new JsonResult<>(true);
    }

    public static <T> JsonResult<T> success(ResultCode resultCode, T data) {
        return new JsonResult<>(true, resultCode, data);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(true, ResultCode.SUCCESS, data);
    }

    public static JsonResult<Void> success(ResultCode resultCode) {
        return new JsonResult<>(true, resultCode);
    }

    public static JsonResult<Void> fail() {
        return new JsonResult<>(false);
    }

    public static JsonResult<Object> fail(ResultCode resultCode) {
        return new JsonResult<>(false, resultCode);
    }

    public static JsonResult<Void> fail(ResultCode resultCode, String message) {
        return new JsonResult<>(false, resultCode, message);
    }

    public static <T> JsonResult<T> fail(T data) {
        return new JsonResult<>(false, data);
    }

    public static JsonResult<Void> successOrFail(Boolean flag) {
        if (flag) {
            return success();
        } else {
            return fail();
        }
    }

    public static <T> JsonResult<T> successOrFail(Boolean flag, T data) {
        if (Boolean.TRUE.equals(flag)) {
            return success(data);
        } else {
            return fail(data);
        }
    }


    public static <T> JsonResult<T> successInSocket(ResultCode resultCode, T data) {
        return new JsonResult<>(resultCode, data);
    }

}
