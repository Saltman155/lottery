package com.superywd.library.script.compiler.impl;

import com.superywd.library.script.compiler.ScriptCompiler;

/**
 * 脚本编译器的Javac实现
 * @author 迷宫的中心
 * @date 2019/4/18 13:49
 */
public class JavacScriptCompiler implements ScriptCompiler {

    @Override
    public String[] getSupportedFileTypes() {
        return new String[]{"java"};
    }
}
