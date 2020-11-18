import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        try{
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 3444);
            final SocketChannel socketChannel =  SocketChannel.open();
            socketChannel.connect(socketAddress);

            try(Scanner scan = new Scanner(System.in)){
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                String msg;
                while(true){
                    int bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();

                    System.out.println("Enter message for server...");
                    msg = scan.nextLine();
                    socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

                    if("end".equals(msg)) break;

                    bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();
                }
            }finally {
                socketChannel.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
