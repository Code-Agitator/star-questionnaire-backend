package pers.star.questionnaire.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Optional;

/**
 * @author Agitator
 */
public class MybatisUtils {
    /**
     * 初始化分页器
     *
     * @param pageNumber 页码
     * @param pageSize   页大小
     * @param <T>        数据类型
     * @return Page分页用对象
     */
    public static <T> Page<T> initPage(Integer pageNumber, Integer pageSize) {
        pageNumber = Optional.ofNullable(pageNumber).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(6);
        return new Page<>(pageNumber, pageSize);
    }


}
