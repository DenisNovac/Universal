package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

// как бы стандартная фабрика новых пайпов, которая цепляет все необходимые хендлеры
public class SimpleNettyServerInitializer extends ChannelInitializer<SocketChannel>{
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new StringDecoder()); // new StringDecoder() включает чарсет системы
        pipeline.addLast(new StringEncoder());
		pipeline.addLast(new SimpleNettyServerHandler());
	}
}
