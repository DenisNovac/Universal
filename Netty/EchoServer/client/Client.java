package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	
	public Client(int port) {
		EventLoopGroup workerGroup=null;
		try {
			workerGroup = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap(); // То же, что ServerBootstrap
			boot.group(workerGroup); // Если создать одну группу, то
			// она используется как босс и рабочий. Но босс-группа не нужна клиенту
			boot.channel(NioSocketChannel.class);
			// вместо NioServerSocketChannel используем это для клиента
			boot.option(ChannelOption.SO_KEEPALIVE, true);
			// childOption нет, т.к. у клиента нет родительского потока boss
			boot.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) {
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			
			// Запуск клиента. Connect вместо bind() у сервера.
			ChannelFuture future = boot.connect("localhost",port).sync();
			
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		} finally {
			workerGroup.shutdownGracefully();
		}
		
		
		
	}
	
	
	
}
