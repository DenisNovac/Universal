package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServer {

	public SimpleNettyServer(int port) {
		System.out.println("Welcome to the server side!");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group (bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new SimpleNettyServerInitializer());
			b.bind(port).sync().channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
		}
		
		
	}
	
	
	
	
}
