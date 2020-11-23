import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
 
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
public class ConnectionCountHandler extends ChannelInboundHandlerAdapter {
 
    //这里用来对连接数进行记数,每两秒输出到控制台
    private static final AtomicInteger nConnection = new AtomicInteger();
 
    static {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("connections: " + nConnection.get());
        }, 0, 2, TimeUnit.SECONDS);
    }
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        nConnection.incrementAndGet();
    }
 
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        nConnection.decrementAndGet();
    }
}