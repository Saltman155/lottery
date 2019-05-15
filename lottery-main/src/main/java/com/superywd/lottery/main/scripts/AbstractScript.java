package com.superywd.lottery.main.scripts;

import com.superywd.lottery.main.model.TbAward;

/**
 * 顶层算法实现脚本
 * @author 迷宫的中心
 * @date 2019/5/15 22:05
 */
public abstract class AbstractScript {


    /**
     * 抽奖动作
     * @return  抽奖结果
     */
    public abstract TbAward draw();

}
