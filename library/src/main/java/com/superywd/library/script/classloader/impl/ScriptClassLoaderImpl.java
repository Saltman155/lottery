package com.superywd.library.script.classloader.impl;

import com.superywd.library.script.classloader.ClassFileManager;
import com.superywd.library.script.classloader.ScriptClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * @author 迷宫的中心
 * @date 2019/4/18 17:13
 */
public class ScriptClassLoaderImpl extends ScriptClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(ScriptClassLoaderImpl.class);

    /**类缓存管理器*/
    private final ClassFileManager classFileManager;

    public ScriptClassLoaderImpl(ClassFileManager classFileManager) {
        super(new URL[] {}, ScriptClassLoaderImpl.class.getClassLoader());
        this.classFileManager = classFileManager;
    }

    public ScriptClassLoaderImpl(ClassFileManager classFileManager, ClassLoader parent) {
        super(new URL[] {}, parent);
        this.classFileManager = classFileManager;
    }

    @Override
    public Set<String> getCompiledClasses() {
        Set<String> compiledClasses = classFileManager.getCompiledClasses().keySet();
        return Collections.unmodifiableSet(compiledClasses);
    }

    @Override
    public byte[] getByteCode(String className) {
        return new byte[0];
    }

    @Override
    public Class<?> getDefinedClass(String name) {
        return null;
    }

    @Override
    public void setDefinedClass(String name, Class<?> clazz) throws IllegalArgumentException {

    }

    public ClassFileManager getClassFileManager() {
        return classFileManager;
    }
}
