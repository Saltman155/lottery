package com.superywd.lottery.main.scripts;

import com.superywd.library.script.ScriptManager;
import com.superywd.library.script.classlistener.AggregatedClassListener;
import com.superywd.lottery.main.scripts.handler.ScriptClassListener;
import com.superywd.lottery.main.scripts.metadata.ScriptName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 迷宫的中心
 * @date 2019/4/18 14:46
 */
public class ScriptService {

    private static final Logger logger = LoggerFactory.getLogger(ScriptManager.class);

    private static final String scriptPath = "./data/scripts/algorithm.xml";

    private final Map<String,Class<? extends AbstractScript>> scriptMap = new HashMap<>();

    private ScriptManager manager;

    public void start(){
        if(manager != null){
            logger.warn("重复初始化！");
            return;
        }
        File file = new File(scriptPath);
        if(!file.exists() || !file.isFile()){
            String errorMessage = String.format("脚本配置文件 %s 不存在！",scriptPath);
            logger.error(errorMessage);
            throw new Error(errorMessage);
        }
        try {
            AggregatedClassListener listener = new AggregatedClassListener();
            listener.addClassListener(new ScriptClassListener());
            manager = new ScriptManager();
            manager.setGlobalClassListener(listener);
            manager.load(file);
        } catch (Exception e) {
            logger.error("启动脚本引擎失败！");
            throw new Error(e);
        }
        logger.info("抽奖脚本编译模块初始化成功！");
    }

    public void registerClass(Class<? extends AbstractScript> clazz){
        ScriptName nameAnnotation = clazz.getAnnotation(ScriptName.class);
        if (nameAnnotation != null) {
            scriptMap.put(nameAnnotation.name(), clazz);
            logger.info("载入名为 {} 的判奖脚本...",nameAnnotation.name());
        }
    }

    private static class SingletonHolder {
        private static ScriptService instance = new ScriptService();
    }

    public static ScriptService getInstance(){
        return SingletonHolder.instance;
    }

}
