package com.superywd.lottery.main.scripts;

import com.superywd.library.script.ScriptManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author 迷宫的中心
 * @date 2019/4/18 14:46
 */
public class ScriptService {

    private static final Logger logger = LoggerFactory.getLogger(ScriptManager.class);

    private static final String scriptPath = "./config/scripts/algorithm.xml";

    public static void start(){
        File file = new File(scriptPath);
        if(!file.exists() || !file.isFile()){
            String errorMessage = String.format("脚本配置文件 %s 不存在！",scriptPath);
            logger.error(errorMessage);
            throw new Error(errorMessage);
        }
        try {
            SingletonHolder.instance.load(file);
        } catch (Exception e) {
            logger.error("启动脚本引擎失败！");
            throw new Error(e);
        }

    }

    public static class SingletonHolder {
        protected static ScriptManager instance = new ScriptManager();
    }

    public static ScriptManager getScriptManager(){
        return SingletonHolder.instance;
    }
}
