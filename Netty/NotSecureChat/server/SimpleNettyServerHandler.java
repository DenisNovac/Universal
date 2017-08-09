package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleNettyServerHandler extends SimpleChannelInboundHandler<String>{
	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	// общий список всех каналов в чате
	
	// действия при активации канала
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		ctx.writeAndFlush("Test message from server recived.");
		channels.add(ctx.channel()); // добавили новый канал в общий список
	}
	
	
	// Рассылает полученное сообщение по всем каналам
	// метод channelRead0 вызывается при каждом 
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		for (Channel c:channels) {
			 if (c != ctx.channel()) {
	                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg);
            } else {
                c.writeAndFlush("[you] " + msg);
            }
		}
		if (msg.toLowerCase().equals("bye")) {
			ctx.close();
		}
		
	}
	
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
