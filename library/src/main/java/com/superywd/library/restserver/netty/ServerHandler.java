package com.superywd.library.restserver.netty;

import com.superywd.library.restserver.ServerContext;
import com.superywd.library.restserver.ServerHandlerExecutor;
import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.http.HttpResponse;
import com.superywd.library.restserver.http.impl.HttpRequestImpl;
import com.superywd.library.restserver.http.impl.HttpResponseImpl;
import com.superywd.library.restserver.router.Router;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.superywd.library.restserver.netty.ChannelHolder.*;

/**
 * 业务处理handler
 * @author 迷宫的中心
 * @date 2019/4/3 11:28
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    //应用上下文对象
    private final ServerContext context;

    protected ServerHandler(ServerContext context){
        this.context = context;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServerHandlerExecutor executor = context.getExecutor();
        if(executor == null){
            handleHttpRequest(ctx, msg);
            return;
        }
        executor.execute(() -> handleHttpRequest(ctx, msg));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常，关闭连接
        logger.error(cause.getMessage(),cause);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e == IdleStateEvent.READER_IDLE_STATE_EVENT) {
                ctx.channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    private void handleHttpRequest(ChannelHandlerContext ctx, Object msg){
        FullHttpRequest request = (FullHttpRequest) msg;
        FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);

        boolean isKeepAlive = HttpUtil.isKeepAlive(request);
        HttpUtil.setKeepAlive(response, isKeepAlive);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.headers().set(HttpHeaderNames.SERVER, "netty-restful-server");

        ChannelFuture channelFuture = null;
        try{
            set(ctx.channel());
            HttpRequest _request = new HttpRequestImpl(request,context.getContextPath());
            HttpResponse _response = new HttpResponseImpl(response);
            //获取请求处理链对象
            Router router = context.getRegistry().findRouteController(_request);
            //如果路由没有找到，返回404
            if(router == null){
                response.setStatus(HttpResponseStatus.NOT_FOUND);
                ByteBufUtil.writeUtf8(response.content(),
                        String.format("Resource not found, url: [%s], http_method: [%s]", _request.getRequestURI(),
                                _request.getRequestMethod()));
            } else{
                router.invoke(_request, _response);
            }
        }catch (Exception ex){
            //异常处理返回500
            logger.error("handle http request error", ex);
            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            ByteBufUtil.writeUtf8(response.content(), ex.toString());
        }finally {
            //一些结束操作
            unset();
            if (isKeepAlive) {
                HttpUtil.setContentLength(response, response.content().readableBytes());
            }
            channelFuture = ctx.channel().writeAndFlush(response);
            if (!isKeepAlive && channelFuture != null) {
                channelFuture.addListener(ChannelFutureListener.CLOSE);
            }
            ReferenceCountUtil.release(msg);
        }

    }


}
