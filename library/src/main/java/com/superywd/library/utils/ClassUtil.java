package com.superywd.library.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author 迷宫的中心
 * @date 2019/4/17 11:23
 */
public class ClassUtil {

    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 根据指定的类，创造其对象
     * @param clazz         类描述
     * @param types         构造器类型
     * @param params        构造器实参
     * @return              类对象
     * @throws NoSuchMethodException        找不到对应的构造器
     */
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


    /**
     * 获取一个jar包下所有的类的全类名
     * @param file          jar文件
     * @return              所有的类
     * @throws IOException  io错误
     */
    public static Set<String> getClassNamesFromJarFile(File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException(String.format("文件 %s 不是有效的jar文件！",file.getAbsolutePath()));
        }
        Set<String> result = new HashSet<String>();
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    //去掉尾部的.class
                    name = name.substring(0, name.length() - 6);
                    name = name.replace('/', '.');
                    result.add(name);
                }
            }
            return result;
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    logger.error("关闭对jar文件 {} 的读取失败！",jarFile.getName());
                }
            }
        }
    }


}
