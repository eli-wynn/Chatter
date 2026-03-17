
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
// Server

class Server {


    public static void main(String[] args) {
        int port = 5000;
        ChatRoom chatRoom = new ChatRoom("Elis the goat");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                try{
                    ClientHandler clientHandler = new ClientHandler(clientSocket, chatRoom);
                    new Thread(clientHandler).start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}