package com.superywd.library.restserver.netty;

import com.superywd.library.restserver.ServerBuilder;
import com.superywd.library.restserver.ServerContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 连接初始化
 * @author 迷宫的中心
 * @date 2019/4/3 10:18
 */
public class ChildChannelInitializer extends ChannelInitializer<Channel> {

    private final ServerBuilder builder;

    private final ServerContext context;

    public ChildChannelInitializer(ServerBuilder builder,ServerContext context) {
        this.builder = builder;
        this.context = context;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //TODO： 这里我看书上还用了另外两个编解码器，为什么这里用一个就可以？
        pipeline.addLast("codec",new HttpServerCodec());
        //数据包长度限制
        pipeline.addLast("encoder",new HttpObjectAggregator(builder.getMaxPacketLength()));
        //心跳事件检查
        pipeline.addLast(new IdleStateHandler(builder.getMaxReadIdleTime(), 0, 0));
        //业务处理
        pipeline.addLast(new ServerHandler(context));
    }
}
