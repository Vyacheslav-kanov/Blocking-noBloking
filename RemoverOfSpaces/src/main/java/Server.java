import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        try {
            final ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress("localhost", 3444));

            while (true){
                System.out.println("Wait connect ...");
                try(SocketChannel socketChannel = serverChannel.accept()) {
                    System.out.println("Connect!");

                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                    while(socketChannel.isConnected()){
                        socketChannel.write(ByteBuffer.wrap(("Server: Введите предложение для удаления в нем лишних пробелов").getBytes(StandardCharsets.UTF_8)));

                        int bytesCount = socketChannel.read(inputBuffer);
                        final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        System.out.println("Получено сообщение: " + msg);

                        String offer = msg;
                        offer = msg.replaceAll("\\s+", " ");

                        socketChannel.write(ByteBuffer.wrap(("Server: Лишние пробелы удалены: " + offer).getBytes(StandardCharsets.UTF_8)));
                        System.out.println("Отправлено сообщение: " + offer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("Server stopped");
        }
    }
}
