package com.superywd.lottery.manage.controller.handler;

import com.superywd.lottery.manage.utils.ResultPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 控制器抛出的异常控制
 * @author: 迷宫的中心
 * @date: 2019/3/28 11:52
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    /**
     * 处理参数缺失的异常
     * @param e     控制层抛出的异常
     * @return      返回结果
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Object catchMissRequestParameterException(Exception e){
        return ResultPack.failInstance(-1,"请求参数缺失");
    }

    /**
     * 参数错误的异常
     * @param e     控制层抛出的异常
     * @return      返回结果
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public Object catchMethodArgumentTypeMismatchException(Exception e){
        if(e.getMessage() == null || "".equals(e.getMessage().trim())) {
            return ResultPack.failInstance(-1, "请求参数缺失");
        }
        return ResultPack.failInstance(-1,e.getMessage());
    }


    /**
     * 处理其他的错误
     * @return      控制层抛出的异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object catchOtherException(Exception e){
        logger.error(e.getMessage(),e);
        return ResultPack.failInstance(-1,"系统繁忙");
    }


}
