package server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * Возвращает принятые данные
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	// Сам по себе реализует интерфейсы ChannelInboundHandler.
	// ChannelInboundHandler просто несёт в себе методы хендлера.
	
	// Метод вызывается при получении соединения
	@Override
	public void channelActive (final ChannelHandlerContext ctx) {
		System.out.println("Someone just got connected!");
		String msg = "Connection to server complete.";
		final ChannelFuture f = ctx.writeAndFlush(msg);
	
		// добавляем слушателя, который удостоверится, что сообщение ушло.
		f.addListener( new ChannelFutureListener() {
			@Override
			public void operationComplete (ChannelFuture future) {
				assert f == future;
				ctx.writeAndFlush("Test for ya.");
			}
		});
	}
	
	
	@Override
	public void channelRead (ChannelHandlerContext ctx, Object msg) {
		// Метод вызывается сообщением любого типа - Object.
		// В этом примере тип сообщения - ByteBuf.
		ctx.write(msg);
		// ChannelHandlerContext содержит операции для IO.
		// Тут используем write(), чтобы отправить полученное сообщение обратно.
		// метод write() автоматически освобождает контекст.
		
		ctx.flush();
		// write() хранит сообщение в буффере, пока его не флушат.
		// ctx.writeAndFlush(msg)  - альтернатива.
		
		
		// Для Discard протокола хендлер должен игнорировать сообщение.
		// В конце работы channelRead обязан всегда освобождаться методом release().
	}
	
	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) {
		// Закрывает соединение при получении исключения.
		// Исключение может возникнуть из-за IO-ошибки или в хендлере.
		// В основном исключения нужно логать, а его канал закрывать.
		// Можно отослать код ошибки перед закрытием соединения.
		cause.printStackTrace();
		ctx.close();
	}
}
