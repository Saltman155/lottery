package com.superywd.library.script.compiler;

/**
 * 脚本引擎编译接口
 *      这个接口声明了一个脚本引擎的编译器应该具有的方法
 *      可以使用groovy来实现它，但目前使用的是原生的javac,因为其生成的字节码效率更高
 * @author 迷宫的中心
 * @date 2019/4/18 13:46
 */
public interface ScriptCompiler {


    /**
     * 支持的文件类型（文件名后缀）
     * @return      默认的是 .java
     */
    String[] getSupportedFileTypes();
}
