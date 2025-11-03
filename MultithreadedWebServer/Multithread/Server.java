import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        return (clientSocket)->{
                try(PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(),true);
                    BufferedReader fromSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                    toSocket.println("Hello from server "+clientSocket.getLocalSocketAddress());
                    String line = fromSocket.readLine();
                    System.out.println("Response from Client " + line);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
        };
    }
    public static void main(String [] args){
        int port = 8050; // Default port
        Server server = new Server();
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server started on port: " + port);
            while(true){
                Socket clientSocket = serverSocket.accept();

                Thread thread = new Thread(()->server.getConsumer().accept(clientSocket));
                thread.start();
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}