package com.superywd.lottery.manage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 迷宫的中心
 * @date 2019/3/26 15:42
 */
@RequestMapping(path = "/test")
@RestController
public class TestController {


    @RequestMapping(path = "/test1",method = RequestMethod.GET)
    public Object testApi(){
        return "测试一下";
    }


}
