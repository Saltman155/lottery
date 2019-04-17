package com.superywd.lottery.main.controller;

import com.superywd.library.restserver.annotation.RequestMapping;
import com.superywd.library.restserver.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽奖活动接口控制器
 * @author 迷宫的中心
 * @date 2019/4/17 14:45
 */

@RequestMapping(path = "/{eventId:[0-9]+}")
@RestController
public class LotteryController {

    public static final Logger logger = LoggerFactory.getLogger(LotteryController.class);




}
