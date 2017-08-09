package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleNettyClient {
	
	public SimpleNettyClient(String host, int port) {
		System.out.println("Welcome to the client side!");
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.handler(new SimpleNettyClientInitializer());
			
			// пробуем подключиться:
			Channel ch = b.connect(host,port).sync().channel();
			// громоздкая команда по сути делает следующее:
			// Bootstrap пытается приконнектиться. Метод sync()
			// ждёт этого. А потом мы просто возвращаем ссылку
			// на канал методом channel();
			
			ChannelFuture lastWriteFuture=null;
			BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
			while (true) {
				String line = in.readLine();
				if (line==null) continue;
				
				// отсылаем непустую строку на сервер
				lastWriteFuture = ch.writeAndFlush(line);
				
				if (line.toLowerCase().equals("bye")) {
                    ch.closeFuture().sync();
                    break;
                }
				
				// ждём, пока отправятся все сообщения
				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
		
	}
}
