package com.superywd.library.script;

import com.superywd.library.script.classlistener.ClassListener;
import com.superywd.library.script.compiler.CompilationResult;

import java.io.File;

/**
 * 表示一类脚本的上下文环境
 * @author 迷宫的中心
 * @date 2019/4/18 13:52
 */
public interface ScriptContext {

    /**
     * 初始化脚本上下文，执行编译，以及编译后的切面方法
     */
    void init();

    /**
     * 安全关闭
     * 卸载所有的已加载的类，保存数据
     */
    void shutdown();

    /**
     * 设置依赖库文件
     * @param files         依赖库文件
     */
    void setLibraries(Iterable<File> files);

    /**
     * 设置编译器名称
     * @param className     编译器类名
     */
    void setCompilerClassName(String className);

    /**
     * 设置类装载监听器
     * @param listener      监听器
     */
    void setClassListener(ClassListener listener);

    /**
     * 获取脚本编译结果
     * @return              编译结果
     */
    CompilationResult getCompilationResult();



}
