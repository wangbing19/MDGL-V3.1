package com.vision.util;

import java.lang.reflect.Array;

/**
 * @ClassName ConvertArray
 * @Description Object数组转其他数组
 * @Author lihd
 * @Date 2019/8/10 14:11
 * @Version 3.1
 */
public class ConvertArrayUtil {
    public static <T> T[] convertArray(Class<T> targetType, Object[] arrayObjects) {
        if (targetType == null) {
            return (T[]) arrayObjects;
        }
        if (arrayObjects == null) {
            return null;
        }
        T[] targetArray = (T[]) Array.newInstance(targetType, arrayObjects.length);
        try {
            System.arraycopy(arrayObjects, 0, targetArray, 0, arrayObjects.length);
        } catch (ArrayStoreException e) {
            e.printStackTrace();
        }
        return targetArray;
    }
}
