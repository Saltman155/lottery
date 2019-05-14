package com.superywd.lottery.main.utils;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 22:35
 */
public class ResultUtil {

    public static class Result{
        private Integer code;

        private String message;

        private Object data;

        public Result() {
        }

        public Result(Integer code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
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
    }


    public static final Result successResult(Object data){
        return new Result(0,"success",data);
    }

    public static final Result errorResult(Integer code,String message){
        return new Result(code,message,null);
    }
}
