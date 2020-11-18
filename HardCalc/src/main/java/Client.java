import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        try {
            Socket clientSocket = new Socket("127.0.0.1", 4333);

            try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                Scanner scan = new Scanner(System.in)){

                System.out.println("Server answer: " + in.readLine());

                String letter;
                String answer;
                while (true) {
                    System.out.println("Server answer: " + in.readLine());
                    System.out.println("Server answer: " + in.readLine());

                    System.out.println("Latter to mail..");
                    letter = scan.nextLine();
                    out.println(letter);
                    if(letter.equals("end")) break;
                }

                System.out.println("Server answer: " + in.readLine());
                System.out.println("Server answer: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
