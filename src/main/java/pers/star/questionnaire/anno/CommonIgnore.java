package pers.star.questionnaire.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通用的ignore注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonIgnore {
    /**
     * 是否无视自定义的返回值处理器
     */
    boolean ignoreReturnValueHandler() default false;
}
