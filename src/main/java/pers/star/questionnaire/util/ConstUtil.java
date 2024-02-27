package pers.star.questionnaire.util;


import pers.star.questionnaire.anno.Const;
import pers.star.questionnaire.anno.ConstAlias;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConstUtil {
    private ConstUtil() {

    }

    public static Map<Object, String> toMap(Class<?> clazz) {
        Const constAnnotation = clazz.getAnnotation(Const.class);
        if (Objects.isNull(constAnnotation)) {
            return Collections.emptyMap();
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<Object, String> result = new HashMap<>(declaredFields.length);
        for (Field field : declaredFields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                String name = getNameOrAlias(field);
                try {
                    result.put(field.get(null), name);
                } catch (IllegalAccessException ignore) {

                }
            }
        }
        return result;
    }

    private static String getNameOrAlias(Field field) {
        if (Objects.isNull(field)) {
            return "";
        }
        ConstAlias constAlias = field.getAnnotation(ConstAlias.class);
        if (Objects.nonNull(constAlias)) {
            return constAlias.value();
        }
        return field.getName();
    }

    public static boolean isIn(String constName, Class<?> clazz) {
        return toMap(clazz).containsKey(constName);
    }

}
