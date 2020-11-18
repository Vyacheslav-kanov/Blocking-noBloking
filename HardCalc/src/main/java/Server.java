import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) { //Выбрал Blocking так как требуется передать лишь номер члена ряда фибоначи и нет смысла в NonBlocking.
        start();
    }

    private static void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(4333);

            while (true){
                System.out.println("Wait connect..");

                try(Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

                    System.out.println("Connect!");
                    out.println("Hi " + socket.getInetAddress());
                    out.println("Write \"end\" to exit");
                    out.println("Enter the number of the member of the Fibonacci series");

                    String answer;
                    while ((answer = in.readLine()) != null){
                        out.println("Eco: " + answer);

                        if(answer.equals("end")) {
                            out.println("Cancel");
                            break;
                        }

                        int numFibonacci = Integer.parseInt(answer);

                        out.println("Член " + numFibonacci +  " ряда Фибоначчи = " + getFibonacci(numFibonacci));
                        answer = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getFibonacci(int number){

        long a = 1L, b = 0L, c = 0L;
        for (int i = 0; i < number; i++) {

            c = a + b;
            b = a;
            a = c;
        }
        return b;
    }
}
