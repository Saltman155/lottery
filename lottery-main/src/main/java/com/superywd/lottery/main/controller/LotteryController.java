package com.superywd.lottery.main.controller;

import com.superywd.library.restserver.annotation.PathVariable;
import com.superywd.library.restserver.annotation.RequestMapping;
import com.superywd.library.restserver.annotation.RequestParam;
import com.superywd.library.restserver.annotation.RestController;
import com.superywd.lottery.main.service.LotteryService;
import com.superywd.lottery.main.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽奖活动接口控制器
 * @author 迷宫的中心
 * @date 2019/4/17 14:45
 */

@RequestMapping(path = "/{activityId:[0-9]+}")
@RestController
public class LotteryController {

    public static final Logger logger = LoggerFactory.getLogger(LotteryController.class);

    private final LotteryService lotteryService = new LotteryService();

    /**
     * 获取抽奖活动的详情接口
     * @param activityId    活动id
     * @return              活动详情
     */
    @RequestMapping(path = "/activityInfo")
    public Object activityInfo(@PathVariable(name = "activityId")Integer activityId){
        Object result = null;
        try{
            Object data = lotteryService.getActivityInfo(activityId);
            result = ResultUtil.successResult(data);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = ResultUtil.errorResult(-1,"查询失败！");
        }
        return result;
    }

    /**
     * 获取用户抽奖详情接口
     * @param activityId
     * @param userId
     * @return
     */
    @RequestMapping(path = "/userActiveRecord")
    public Object userActiveRecord(
            @PathVariable(name = "activityId")Integer activityId,
            @RequestParam(name = "userId")Integer userId){
        Object result = null;
        try{
            Object data = lotteryService.getUserActiveRecord(activityId,userId);
            result = ResultUtil.successResult(data);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = ResultUtil.errorResult(-1,"查询失败！");
        }
        return result;
    }

}
