package com.superywd.lottery.main.server;

import com.superywd.lottery.main.server.netty.ChildChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主服务
 * @author 迷宫的中心
 * @date 2019/4/2 9:45
 */
public class LotteryServer {

    private static final Logger logger = LoggerFactory.getLogger(LotteryServer.class);

    private final LotteryServerBuilder builder;

    public LotteryServer(LotteryServerBuilder builder) {
        this.builder = builder;
    }

    public void start(){
        //TODO 注解扫描装配到上下文
        //接受请求线程池
        EventLoopGroup acceptor = new NioEventLoopGroup(builder.getAcThreadCount());
        //处理连接读写请求线程池
        EventLoopGroup worker = new NioEventLoopGroup(builder.getIoTreadCount());
        ServerBootstrap bootstrap = null;
        try{
            bootstrap = new ServerBootstrap()
                    .group(acceptor,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelInitializer(builder));

            ChannelFuture future = bootstrap.bind(builder.getPort()).sync();
            logger.info("网络服务已在端口 {} 上启动！",builder.getPort());
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("网络框架启动失败！");
            logger.error(e.getMessage(),e);
        }finally {
            worker.shutdownGracefully();
            acceptor.shutdownGracefully();
        }
    }
}
