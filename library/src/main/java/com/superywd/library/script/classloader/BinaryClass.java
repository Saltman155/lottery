package com.superywd.library.script.classloader;

import com.sun.tools.javac.file.BaseFileObject;

import java.io.*;
import java.net.URI;

/**
 * 这个类简单的维护了类名，类对象与字节码的关联关系
 * @author 迷宫的中心
 * @date 2019/4/18 17:36
 */
public class BinaryClass extends BaseFileObject {

    /**类名*/
    private final String name;
    /**字节码容器*/
    private final ByteArrayOutputStream byteData = new ByteArrayOutputStream();
    /**对应的类对象*/
    private Class<?> definedClass;

    protected BinaryClass(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public String getShortName() {
        String[] tmp = name.split("\\.");
        return tmp[tmp.length-1];
    }

    @Override
    protected String inferBinaryName(Iterable<? extends File> iterable) {
        return name;
    }

    @Override
    public Kind getKind() {
        //Class类型
        return Kind.CLASS;
    }

    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        return Kind.CLASS.equals(kind);
    }

    @Override
    public URI toUri() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name + ".class";
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(byteData.toByteArray());
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return byteData;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Writer openWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BinaryClass){
            return this.name.equals(((BinaryClass) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Class<?> getDefinedClass() {
        return definedClass;
    }

    public void setDefinedClass(Class<?> definedClass) {
        this.definedClass = definedClass;
    }
}
