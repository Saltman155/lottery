package com.superywd.library.restserver;

import java.util.concurrent.*;

/**
 * 自定义业务线程池（由ThreadPoolExecutor实现）
 * @author 迷宫的中心
 * @date 2019/4/3 11:38
 */
public class ServerHandlerExecutor extends ThreadPoolExecutor {

    public ServerHandlerExecutor(int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue) {
        //线程池设置
        super(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        //执行业务操作的开始切点，可以记点日志什么的
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        //执行业务操作的结束切点，记点日志或运行时间
    }
}
