package com.superywd.library.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 包扫描类，获取某个路径下所有的类描述对象
 * @author 迷宫的中心
 * @date 2019/4/13 12:43
 */
public class PackageScanner {

    private static final Logger logger = LoggerFactory.getLogger(PackageScanner.class);

    public static List<Class<?>> scan(String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new LinkedList<>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> urls = loader.getResources(packageName.replace('.', '/'));
            while (urls.hasMoreElements()) {
                URI uri = urls.nextElement().toURI();
                switch (uri.getScheme().toLowerCase()) {
                    case "jar":
                        scanFromJarProtocol(loader, classes, uri.getRawSchemeSpecificPart());
                        break;
                    case "file":
                        scanFromFileProtocol(loader, classes, new File(uri).getPath(), packageName);
                        break;
                    default:
                        throw new URISyntaxException(uri.getScheme(), "unknown schema " + uri.getScheme());
                }
            }
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(),e);
        }
        return classes;
    }


    private static Class<?> loadClass(ClassLoader loader, String classPath) {
        try {
            classPath = classPath.substring(0, classPath.length() - 6);
            return loader.loadClass(classPath);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    private static void scanFromFileProtocol(ClassLoader loader, List<Class<?>> classes, String dir, String packageName) {
        try {
            List<String> classNames = listAllClassNames(dir);
            classNames.forEach(className -> {
                try {
                    classes.add(loadClass(loader, String.format("%s.%s", packageName,className)));
                } catch (Throwable e) {
                    logger.error(e.getMessage());
                }
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void scanFromJarProtocol(ClassLoader loader, List<Class<?>> classes, String fullPath) {
        final String jar = fullPath.substring(0, fullPath.lastIndexOf('!'));
        final String parent = fullPath.substring(fullPath.lastIndexOf('!') + 2);
        JarEntry e = null;

        JarInputStream jarReader = null;
        try {
            jarReader = new JarInputStream(new URL(jar).openStream());
            while ((e = jarReader.getNextJarEntry()) != null) {
                String className = e.getName();
                if (!e.isDirectory() && className.startsWith(parent) && className.endsWith(".class")
                        && !className.contains("$")) {
                    className = className.replace('/', '.');
                    classes.add(loadClass(loader, className));
                }
                jarReader.closeEntry();
            }
        } catch (IOException error) {
            logger.error(error.getMessage(),error);
        } finally {
            try {
                if (jarReader != null)
                    jarReader.close();
            } catch (IOException exp) {
                logger.error(exp.getMessage(),exp);
            }
        }
    }

    private static List<String> listAllClassNames(String classpath) {
        List<String> classNames = new ArrayList<>();

        Iterator<File> iterator = FileUtils.iterateFiles(new File(classpath), new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return accept(file,file.getName());
            }
            @Override
            public boolean accept(File file, String s) {
                return s.contains(".class");
            }
        }, TrueFileFilter.INSTANCE);
        int index = classpath.endsWith(File.separator) ? classpath.length() : classpath.length() + 1;
        while(iterator.hasNext()) {
            File classFile = iterator.next();
            String className = classFile.getAbsolutePath().substring(index).replace(File.separatorChar, '.');
            classNames.add(className);
        }
        return classNames;
    }

}
