package com.vision.util;

import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ListUtils
 * @Description list各种类型转换，验空，复制
 * @Author lihd
 * @Date 2019/8/1 19:52
 * @Version 3.1
 */
public class ListUtils {
    public ListUtils() {
    }

    public static List<Long> listLong(String strings) {
        return listLong(strings, ",");
    }

    public static List<Long> listLong(String strings, String splitor) {
        if (strings != null && !strings.trim().isEmpty()) {
            if (splitor == null) {
                splitor = ",";
            }

            List<Long> ret = new ArrayList<>();
            Arrays.asList(strings.split(splitor)).stream().mapToLong((t) -> {
                return Long.valueOf(t).longValue();
            }).forEach((string) -> {
                ret.add(string);
            });
            return ret;
        } else {
            return Collections.emptyList();
        }
    }

    public static List<String> listString(String strings) {
        return listString(strings, ",");
    }

    public static List<String> listString(String strings, String splitor) {
        if (strings != null && !strings.trim().isEmpty()) {
            if (splitor == null) {
                splitor = ",";
            }

            List<String> ret = new ArrayList();
            Arrays.asList(strings.split(splitor)).forEach((string) -> {
                ret.add(string);
            });
            return ret;
        } else {
            return Collections.emptyList();
        }
    }

    public static <E> E fetchOne(Collection<E> targets) {
        if (targets != null && !targets.isEmpty()) {
            Iterator var1 = targets.iterator();
            if (var1.hasNext()) {
                E e = (E) var1.next();
                return e;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T copy(Object source, Class<T> target) {
        try {
            T o = target.newInstance();
            BeanUtils.copyProperties(source, o);
            return o;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> copyList(Collection source, Class<T> target) {
        return source == null ? Collections.emptyList() : (List) source.stream().map((s) -> {
            return copy(s, target);
        }).collect(Collectors.toList());
    }

    public static boolean isArrayNotEmpty(Collection array) {
        return !isArrayEmpty(array);
    }

    public static boolean isArrayEmpty(Collection array) {
        return array == null || array.isEmpty();
    }

}
