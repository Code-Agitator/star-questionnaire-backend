package pers.star.questionnaire.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtil {


    /**
     *
     */
    public static <T, E> IPage<T> map(IPage<E> page, Function<E, T> function) {
        IPage<T> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setPages(page.getPages());
        List<T> records = page.getRecords().stream().map(function).collect(Collectors.toList());
        pageResult.setRecords(records);
        return pageResult;
    }

    public static <T> IPage<T> foreach(IPage<T> page, Consumer<T> consumer) {
        List<T> records = page.getRecords();
        if (ObjectUtils.isEmpty(records)) {
            return page;
        }
        records.forEach(consumer);
        return page;
    }


    public static <T> IPage<T> emptyPage(IPage<?> page) {
        return Page.of(page.getCurrent(), page.getSize());
    }
}
