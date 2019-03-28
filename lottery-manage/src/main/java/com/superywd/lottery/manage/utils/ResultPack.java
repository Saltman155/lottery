package com.superywd.lottery.manage.utils;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/28 11:35
 */
public class ResultPack {

    private int code;

    private String message;

    private Object data;

    private ResultPack(){}

    public static final ResultPack successInstance(){
        return successInstance(null);
    }

    public static final ResultPack successInstance(Object data){
        ResultPack result = new ResultPack();
        result.code = 0;
        result.message = "success";
        result.data = data;
        return result;
    }

    public static final ResultPack failInstance(int code,String message){
        return failInstance(code, message,null);
    }

    public static ResultPack failInstance(int code,String message,Object data){
        ResultPack result = new ResultPack();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultPack{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
