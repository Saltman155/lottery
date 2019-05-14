package com.superywd.lottery.main.service;

import com.superywd.lottery.main.dao.ActivityDao;

/**
 * 业务处理
 * @author 迷宫的中心
 * @date 2019/5/14 22:32
 */
public class LotteryService {

    public Object getActivityInfo(int activityId){
        return ActivityDao.getActivityById(activityId);
    }

}
