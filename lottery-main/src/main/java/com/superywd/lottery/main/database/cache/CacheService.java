package com.superywd.lottery.main.database.cache;

/**
 * 缓存服务
 * @author 迷宫的中心
 * @date 2019/5/15 2:04
 */
public class CacheService {

    private CacheTool tool;

    private CacheService(){

    }


    public static CacheTool getTool() {
        return getInstance().tool;
    }

    private static final class SingletonHolder {
        private static final CacheService INSTANCE = new CacheService();
    }

    /**
     * 单例方法
     * @return  单例对象
     */
    public static CacheService getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
