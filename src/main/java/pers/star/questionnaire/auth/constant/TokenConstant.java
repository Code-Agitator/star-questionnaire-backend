package pers.star.questionnaire.auth.constant;

/**
 * Token 模块的一些静态常量
 */
public class TokenConstant {

    public static final String TOKEN_FIELD = "etoken";

    private TokenConstant() {

    }

    /**
     * token 中 subject 参数的 field
     */
    public static class TokenSubjectField {
        /**
         * 用户参数
         */
        public static final String USER_INFO = "userInfo";
    }
}
