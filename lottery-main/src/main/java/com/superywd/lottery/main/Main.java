package com.superywd.lottery.main;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.superywd.library.restserver.Server;
import com.superywd.library.restserver.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * 启动类
 * @author 迷宫的中心
 * @date 2019/4/2 16:52
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static int port = 10086;

    /**日志配置初始化*/
    private static void initLogger(){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        File configFile = new File("./config/slf4j-logback.xml");
        if(!configFile.exists() || !configFile.isFile()){
            throw new Error("载入日志文件配置失败！请检查./config/目录下是否有正常的slf4j-logback.xml文件！");
        }else{
            try {
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(context);
                context.reset();
                configurator.doConfigure(configFile);
                StatusPrinter.printInCaseOfErrorsOrWarnings(context);
            } catch (JoranException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }



    public static void main(String[] args) {
        initLogger();
        loadParameters(args);
        Server server = ServerBuilder.byPort(port).build();
        server.start();
    }

    private static void loadParameters(String[] args){
        if(args == null || args.length == 0){
            return;
        }
        for(String parameter : args){
            String[] keyAndValue = parameter.split(":");
            if(keyAndValue.length != 2){ continue; }
            switch (keyAndValue[0].toLowerCase()){
                case "-port" : port = Integer.valueOf(keyAndValue[1]);
                    break;
                case "-param2" : //nothing
                    break;
                case "-param3" : //nothing
                    break;
                default :
                    break;
            }
        }
    }
}
