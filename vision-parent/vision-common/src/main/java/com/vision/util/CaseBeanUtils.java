package com.vision.util;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName CaseBeanUtils
 * @Description
 * @Author lihd
 * @Date 2019/8/1 19:54
 * @Version 3.1
 */
public class CaseBeanUtils {

    /**
     * 实体类之间的转换
     *
     * @param entity
     * @param doClass
     * @return
     */
    public static <T> T entityToClass(Object entity, Class<T> doClass) {
        if (entity == null) {
            return null;
        }
        if (doClass == null) {
            return null;
        }
        try {
            T newInstance = doClass.newInstance();
            BeanUtils.copyProperties(entity, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

}
