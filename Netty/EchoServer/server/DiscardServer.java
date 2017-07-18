package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Выбрасывает любую полученую информацию.
 */
public class DiscardServer {
	private int port;
	
	
	public DiscardServer(int port) {
		this.port = port;
		this.run();
	}
	
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		// NioEventLoopGroup - мультпоточная петля событий, принимающая IO операции.
		// Netty предоставляет множество EventLoopGroup для разных типов передач.
		// Мы создаём сервер, так что нам необходимо две петли.
		// bossGroup принимает подключения
		// workerGroup контроллирует трафик принятого подключения после того,
		// как boss принял подключение и зарегистрировал его в воркере.
		// Количество используемых Тредов и их расположение в созданном
		// канале зависит от EventLoopGroup и может быть модифицировано конструктором.
		
		try {
			ServerBootstrap serverBoot = new ServerBootstrap();
			// ServerBootstrap поднимает сервер. Можно настроить сервер через
			// Канал напрямую, хотя обычно это не нужно.
			serverBoot.group(bossGroup, workerGroup);
			
            serverBoot.channel(NioServerSocketChannel.class) ;
			// указали использовать NioServerSocketChannel как класс,
			// на основе которого будем создавать новые каналы
            		  
            serverBoot.childHandler(new ChannelInitializer<SocketChannel>() {
		    // указанный тут хендлер будет всегда использован новыми каналами.
		    // ChannelInitializer - специальный хендлер, созданный с целью
		    // помочь пользователю конфигурировать новый канал.
		    // Обычно вы хотите конфигурировать ChannelPipeline нового канала
            // с помощью нескольких хендлеров вроде DiscardServerHandler
            // для работы вашего серверного приложения. С усложнением приложения
            // вы будете добавлять больше хендлеров в pipeline.
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoServerHandler()); // указали наш собственный хендлер
                }
			});
            
		    serverBoot.option(ChannelOption.SO_BACKLOG, 128);  
		    serverBoot.childOption(ChannelOption.SO_KEEPALIVE, true);
		    // Можно так же настроить параметры, специфичные для канала.
		    // У нас сервер, так что мы можем настроить опции сокета
		    // tcpNoDelay и опцию keepAlive, и прочие.
		    // option() для NioServerSocketChannel, который принимает подключения.
		    // childOption() для принятого родителем ServerChannel канала, который 
		    // окажется NioServerSocketChannel в этом случае
			
			ChannelFuture channelFuture = serverBoot.bind(port).sync();
			// Биндим и начинаем принимать соединения. Биндимся на принятый порт.
			// можно вызвать bind сколько угодно раз (с разными портами)
			
			
			
			// Ждём, пока серверный сокет не закрыт
			// В этом примере это не произойдет, но это можно сделать, чтобы
			// выключить сервер ИЗЯЩНО
			channelFuture.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();

		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
} // end of DiscardServer class
