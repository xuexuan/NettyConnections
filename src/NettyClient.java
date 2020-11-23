import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
 
public class NettyClient {
 
    //服务端的IP
    private static final String SERVER_HOST = "127.0.0.1";
 
    static final int BEGIN_PORT = 10000;
    static final int N_PORT = 100;
 
    public static void main(String[] args) {
        new NettyClient().start(BEGIN_PORT, N_PORT);
    }
 
    public void start(final int beginPort, int nPort) {
        System.out.println("client starting....");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
            }
        });
 
        int index = 0;
        int port;
        //从10000的端口开始，按端口递增的方式进行连接
        while (!Thread.interrupted()) {
            port = beginPort + index;
            try {
        		 ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, port);
                 channelFuture.addListener((ChannelFutureListener) future -> {
                     if (!future.isSuccess()) {
                         System.out.println("connect failed, exit!");
                         //System.exit(0);
                     }
                     else
                     {
                     	System.out.println("connect successully.");
                     }
                 });
                 channelFuture.get();
            } catch (Exception e) {
            	System.out.println(e);
            }
 
            if (++index == nPort) {
                index = 0;
            }
        }
    }
}