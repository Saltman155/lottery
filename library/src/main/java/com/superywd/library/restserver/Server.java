package com.superywd.library.restserver;

import com.superywd.library.restserver.netty.ChildChannelInitializer;
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
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    /**应用配置器*/
    private final ServerBuilder builder;
    /**应用上下文*/
    private final ServerContext context;

    public Server(ServerBuilder builder) {
        this.builder = builder;
        //扫描注解装配到上下文对象
        this.context = ServerContext.create(builder);
    }

    public void start(){
        //请求连接事件处理线程池
        EventLoopGroup acceptor = new NioEventLoopGroup(builder.getAcThreadCount());
        //请求读写事件处理线程池
        EventLoopGroup worker = new NioEventLoopGroup(builder.getIoTreadCount());
        ServerBootstrap bootstrap = null;
        try{
            bootstrap = new ServerBootstrap()
                    .group(acceptor,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelInitializer(builder,context));

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
