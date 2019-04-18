package com.superywd.lottery.main.controller;

import com.superywd.library.restserver.annotation.PathVariable;
import com.superywd.library.restserver.annotation.RequestMapping;
import com.superywd.library.restserver.annotation.RequestParam;
import com.superywd.library.restserver.annotation.RestController;
import com.superywd.library.restserver.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试接口控制器
 * @author 迷宫的中心
 * @date 2019/4/17 14:47
 */
@RequestMapping(path = "/test")
@RestController
public class TestController {

    public static final Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @RequestMapping(path = "/test1",method = HttpMethod.GET)
    public Object test(@RequestParam(name = "param",required = false)String param1){
        return null;
    }

    @RequestMapping(path = "/test2/{param1}")
    public Object test(
            @PathVariable(name = "param1")String param1,
            @RequestParam(name = "param2",required = false)String param2){
        logger.info("param1 = {}",param1);
        logger.info("param2 = {}",param2);
        Map<String,Object> result = new HashMap<>();
        result.put("code",0);
        result.put("message","success");
        return result;
    }


}
