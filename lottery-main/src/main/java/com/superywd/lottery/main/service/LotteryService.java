package com.superywd.lottery.main.service;

import com.superywd.lottery.main.dao.ActivityDao;
import com.superywd.lottery.main.model.TbActivity;
import com.superywd.lottery.main.model.dto.ActivityInfoDTO;

/**
 * 业务处理
 * @author 迷宫的中心
 * @date 2019/5/14 22:32
 */
public class LotteryService {

    public Object getActivityInfo(int activityId){
        TbActivity activity = ActivityDao.getActivityById(activityId);
        return activity == null ? null : new ActivityInfoDTO(activity);
    }

    public Object getUserActiveRecord(int activityId,int userId){
        return null;
    }

    public Object lotteryDraw(int activityId,int userId){
        return null;
    }

}
