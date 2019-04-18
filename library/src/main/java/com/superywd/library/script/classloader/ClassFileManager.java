package com.superywd.library.script.classloader;

import com.superywd.library.script.classloader.impl.ScriptClassLoaderImpl;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *此类扩展管理加载的类，它还负责欺骗编译器。不幸的是编译器没有
 *使用类加载器，因此我们必须为每个编译手动传递类数据。
 * @author 迷宫的中心
 * @date 2019/4/18 17:32
 */
public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    /**这个map存储相关的classloader对脚本编译的结果（二进制文件）*/
    private final Map<String, BinaryClass> compiledClasses = new HashMap<>();
    /**相关的ScriptClassLoader*/
    protected ScriptClassLoaderImpl classLoader;
    /**相关的ScriptClassLoader的父级classLoader*/
    protected ScriptClassLoader parentClassLoader;

    public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
        super(compiler.getStandardFileManager(listener, null, null));
    }

    /**
     * 该方法会在脚本编译时调用。
     * 我们将其重写为创建一个自定义的BinaryClass，然后放入缓存中
     * @param location      未使用
     * @param className     类名
     * @param kind          未使用
     * @param sibling       未使用
     * @return              自定义的字节码类关联对象
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        BinaryClass binaryClass = new BinaryClass(className);
        compiledClasses.put(className, binaryClass);
        return binaryClass;
    }

    /**
     * 获取此类文件管理器的类加载器
     * 如果没有类加载器，则创建一个类加载器
     * @param location      未使用
     * @return              对应的类加载器
     */
    @Override
    public ScriptClassLoaderImpl getClassLoader(Location location) {
        if(classLoader == null){
            if (parentClassLoader != null) {
                classLoader = new ScriptClassLoaderImpl(this, parentClassLoader);
            }
            else {
                classLoader = new ScriptClassLoaderImpl(this);
            }
        }
        return classLoader;
    }

    /**
     * 添加依赖文件到这个类文件管理器中
     * @param file                  依赖文件（.jar）
     * @throws IOException
     */
    public void addLibrary(File file) throws IOException {
        ScriptClassLoaderImpl classLoader = getClassLoader(null);
        classLoader.addJarFile(file);
    }

    /**
     * 添加一系列依赖文件到类文件管理器中
     * @param files                 依赖文件迭代器
     * @throws IOException
     */
    public void addLibraries(Iterable<File> files) throws IOException {
        for (File f : files) {
            addLibrary(f);
        }
    }

    /**
     * 获取已经类文文件管理器中维护的类（已经编译的）
     * @return
     */
    public Map<String, BinaryClass> getCompiledClasses() {
        return compiledClasses;
    }

    public void setParentClassLoader(ScriptClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }

}
