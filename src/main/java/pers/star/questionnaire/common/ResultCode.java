package pers.star.questionnaire.common;


import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Agitator
 * @Description 封装响应码和响应信息
 */
@Getter
public enum ResultCode {
    /* 成功 */
    SUCCESS(0, "成功"),

    RESOURCE_NOT_FOUND(404, "资源不存在"),
    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000~1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码或验证码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ADDRESS_NOT_FOUND(2010, "用户地址为找到"),

    USER_WX_LOGIN_FAIL(2021, "用户微信登录失败"),
    USER_PHONE_ALREADY_EXIST(2022, "手机号码已绑定其他账号"),

    /* 好友相关 */
    RECOMMEND_ALREADY_EXIST(2102, "推荐博主已存在"),
    NOT_TO_FOLLOW_YOURSELF(2103, "不能关注自己"),

    /* 私信相关 */
    NOT_TO_CHAT_WITH_YOURSELF(2201, "不能给自己发送消息"),
    CHAT_BATCH_SEND_FAIL(2202, "私信批量发送失败"),
    MESSAGE_REFUSE(2203, "私信发送被拒绝接收"),

    /* 权限相关 */
    NO_PERMISSION(3001, "没有权限"),
    USER_TOKEN_EXPIRES(3002, "token权限认证过期，请重新获取"),
    USER_TOKEN_ILLEGAL(3003, "token不合法"),
    USER_REFRESH_TOKEN_EXPIRES(3004, "refresh_token权限认证过期，请重新获取"),
    USER_REFRESH_TOKEN_ILLEGAL(3005, "refresh_token不合法"),
    /* 店铺相关 */
    STORE_IS_CLOSE(4001, "店铺打烊了"),
    CURRENT_USER_AND_STORE_MISMATCH(4003, "当前用户与指定店铺不匹配"),
    COUPON_IS_NOT_AVAILABLE(4004, "优惠券已失效/库存不足"),
    COUPON_IS_LIMIT(4005, "领取已达上限"),


    /* 菜品相关 */
    CUSTOM_ITEM_NOT_FOUND(40001, "定制项为空"),


    /* 订单相关 */
    ORDER_ITEM_CREATED_FAIL(5001, "订单项添加失败"),
    ORDER_CREATED_FAIL(5002, "订单添加失败"),
    ORDER_NOT_FOUND(5003, "订单未找到"),
    ORDER_ADDRESS_IS_NULL(5004, "外卖订单,地址为空"),
    ORDER_DISHES_WITHOUT_PRICE(5005, "订单菜品价格数据有误"),
    ORDER_CUSTOM_ITEM_IS_NULL(5006, "定制商品定制项为空"),
    ORDER_INPUT_ERROR(5007, "订单输入信息错误"),
    ORDER_WXPAY_ERROR(5008, "微信支付失败"),
    ORDER_INVALID(5009, "无效订单:未支付/金额为0"),

    /* 评论相关 */
    TARGET_TYPE_NOT_AVAILABLE(6001, "targetType参数无效"),
    /* 动态相关 */
    DYNAMIC_IS_NOT_FOUND(6101, "动态不存在"),

    /* 内容审核相关 */
    CONTENT_SENSITIVE(6201, "内容敏感"),

    /* websocket相关 */
    WEBSOCKET_CONNECTION_SUCCESS(101, "socket连接成功"),
    WEBSOCKET_CONNECTION_NOT_SUCCESS(111, "socket连接失败，令牌不正确"),
    WEBSOCKET_TOKEN_NOT_AVAILABLE(112, "socket连接失败，令牌不存在"),
    WEBSOCKET_HAVE_NEW_MESSAGE(120, "socket响应成功,新私信"),
    WEBSOCKET_HAVE_NEW_EVENT_NOTIFY(121, "socket响应成功,新通知"),
    WEBSOCKET_HAVE_NEW_ORDER(122, "新订单"),

    WEBSOCKET_COWJIANG_NEVER_SLEEP(123, "COWJIANG永不睡觉");


    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<Integer, String> toMap() {
        return Arrays.stream(ResultCode.values()).collect(Collectors.toMap(ResultCode::getCode, ResultCode::getMessage));
    }


    /**
     * 通过code获取message
     *
     * @param code 需要查询的code
     * @return code对应的message
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode.getMessage();
            }
        }
        return null;
    }


}
