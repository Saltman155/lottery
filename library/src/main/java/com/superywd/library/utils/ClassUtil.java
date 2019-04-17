package com.superywd.library.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author 迷宫的中心
 * @date 2019/4/17 11:23
 */
public class ClassUtil {

    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    @SuppressWarnings("unchecked")
    public static Object buildInstance(Class clazz,Class[] types,Object[] params) throws NoSuchMethodException {
        Constructor constructor = clazz.getConstructor(types);
        boolean oldAccess = constructor.isAccessible();
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.warn(e.getMessage(),e);
            return null;
        } finally {
            constructor.setAccessible(oldAccess);
        }
    }


}
