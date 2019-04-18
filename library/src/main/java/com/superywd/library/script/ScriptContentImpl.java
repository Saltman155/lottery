package com.superywd.library.script;

import com.superywd.library.script.classlistener.ClassListener;
import com.superywd.library.script.compiler.CompilationResult;
import com.superywd.library.script.compiler.ScriptCompiler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * 脚本上下文具体实现
 * @author 迷宫的中心
 * @date 2019/4/18 15:16
 */
public class ScriptContentImpl implements ScriptContext {

    private static final Logger logger = LoggerFactory.getLogger(ScriptContentImpl.class);

    /**父类型脚本上下文对象*/
    private final ScriptContext parentScriptContext;
    /**脚本文件根节点*/
    private final File root;
    /**依赖的包*/
    private Iterable<File> libraries;
    /**编译结果*/
    private CompilationResult compilationResult;
    /**子类型脚本上下文对象*/
    private Set<ScriptContext> childScriptContexts;
    /**类装载监听器*/
    private ClassListener classListener;
    /**编译器名称*/
    private String compilerClassName;

    public ScriptContentImpl(File root){
        this(root, null);
    }

    public ScriptContentImpl(File root,ScriptContext parent){
        if(root == null){
            throw new NullPointerException("根路径不存在...");
        }
        if (!root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException(String.format("不是正确的根路径: %s",root.getAbsolutePath()));
        }
        this.root = root;
        this.parentScriptContext = parent;
    }



    @Override
    public void init() {
        if (compilationResult != null) {
            logger.error("脚本引擎需要编译器...");
            return;
        }
        ScriptCompiler scriptCompiler = instantiateCompiler();
        //获取到根路径下所有的文件
        Collection<File> files = FileUtils.listFiles(root, scriptCompiler.getSupportedFileTypes(), true);
        //设置父级类加载器
        if (parentScriptContext != null) {
            scriptCompiler.setParentClassLoader(parentScriptContext.getCompilationResult().getClassLoader());
        }
        scriptCompiler.setLibraries(libraries);
        //编译
        compilationResult = scriptCompiler.compile(files);

//        getClassListener().postLoad(compilationResult.getCompiledClasses());
        //处理子类脚本上下文的初始化
        if (childScriptContexts != null) {
            for (ScriptContext context : childScriptContexts) {
                context.init();
            }
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void setLibraries(Iterable<File> files) {

    }

    @Override
    public void setCompilerClassName(String className) {

    }

    @Override
    public void setClassListener(ClassListener listener) {

    }

    @Override
    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    /**
     * 实例化编译器
     * @return                      编译器对象
     */
    protected ScriptCompiler instantiateCompiler() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (getParentScriptContext() != null) {
            classLoader = getParentScriptContext().getCompilationResult().getClassLoader();
        }
        ScriptCompiler compiler;
        try {
            compiler = (ScriptCompiler) Class.forName(getCompilerClassName(),true,classLoader).newInstance();
        } catch (Exception e) {
            logger.error("编译器 {} 加载失败!",getCompilerClassName());
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return compiler;
    }


    public ScriptContext getParentScriptContext() {
        return parentScriptContext;
    }

    public String getCompilerClassName() {
        return compilerClassName;
    }
}
