package com.superywd.library.script.classloader;

import com.superywd.library.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 脚本类加载器
 * @author 迷宫的中心
 * @date 2019/4/18 16:31
 */
public abstract class ScriptClassLoader extends URLClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(ScriptClassLoader.class);

    /**类加载器加载的所有类的类名*/
    private Set<String> libraryClassNames = new HashSet<String>();
    /**类加载器加载的所有的jar文件*/
    private Set<File> loadedLibraries = new HashSet<File>();

    public ScriptClassLoader(URL[] urls) {
        super(urls);
    }

    public ScriptClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ScriptClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //先判断此类加载器中是否有编译的结果（字节码），是则从此类加载器缓存中获取，否则从父类加载器中获取
        boolean isCompiled = getCompiledClasses().contains(name);
        if(!isCompiled) {
            return super.loadClass(name,true);
        }
        //尝试从缓存中获取Class对象
        Class<?> clazz = getDefinedClass(name);
        //如果缓存中获取不到，说明字节码尚未被转换为Class对象
        //则调用父加载器的分析方法构建出Class对象，再放入缓存
        if (clazz == null) {
            byte[] b = getByteCode(name);
            clazz = super.defineClass(name, b, 0, b.length);
            setDefinedClass(name, clazz);
        }
        return clazz;
    }

    /**
     * @param file
     * @throws IOException
     */
    public void addJarFile(File file) throws IOException {
        if(!loadedLibraries.contains(file)){
            Set<String> jarFileClasses = ClassUtil.getClassNamesFromJarFile(file);
            libraryClassNames.addAll(jarFileClasses);
            loadedLibraries.add(file);
        }
    }

    /**
     * 获取所有已经加载的类的名称（不可改动的）
     * @return              已加载类名称
     */
    protected Set<String> getLibraryClassNames(){
        //返回不可改动的视图
        return Collections.unmodifiableSet(libraryClassNames);
    }

    /**
     * 获取所有已经编译的class
     * @return             编译的类名
     */
    public abstract Set<String> getCompiledClasses();

    /**
     * 获取指定类的字节码信息
     * 返回的字节数组应是副本，以保证修改不会受到影响
     * @param className     类名
     * @return              字节码数组
     */
    public abstract byte[] getByteCode(String className);

    /**
     * 从缓存中根据类名获取对应的Class对象
     * @param name          类名
     * @return              Class对象
     */
    public abstract Class<?> getDefinedClass(String name);

    /**
     * 将类名与对应的Class对象放入缓存
     * @param name          类名
     * @param clazz         对应的class对象
     * @throws IllegalArgumentException     参数错误
     */
    public abstract void setDefinedClass(String name, Class<?> clazz) throws IllegalArgumentException;

}
