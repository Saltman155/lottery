package com.superywd.lottery.main.server.netty;

import com.superywd.lottery.main.server.LotteryServerBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * 连接初始化
 * @author 迷宫的中心
 * @date 2019/4/3 10:18
 */
public class ChildChannelInitializer extends ChannelInitializer<Channel> {

    private final LotteryServerBuilder builder;

    public ChildChannelInitializer(LotteryServerBuilder builder) {
        this.builder = builder;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

    }
}
