package com.superywd.library.restserver.netty;


import io.netty.channel.Channel;

/**
 * 保存当前线程环境中的channel对象
 * @author 迷宫的中心
 * @date 2019/4/16 15:51
 */
public class ChannelHolder {

    private static final ThreadLocal<Channel> channelHolder = new ThreadLocal<>();

    /**
     * 设置当前线程上下文中的channel
     * @param channel       当前线程上下文中的channel
     */
    public static void set(Channel channel){
        channelHolder.set(channel);
    }

    /**
     * 取消当前线程上下文中的channel设置
     */
    public static void unset() {
        channelHolder.remove();
    }

    /**
     * 获取当前线程上下文中的channel
     * @return              当前线程上下文中的channel
     */
    public static Channel get(){
       return channelHolder.get();
    }
}
