import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ChatRoom chatRoom;
    private String username;
    private PrintWriter output;

    public ClientHandler(Socket clientSocket, ChatRoom chatRoom) throws IOException {
        this.clientSocket = clientSocket;
        this.chatRoom = chatRoom;
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public void run() {
        try{
            output.println("Chat Initialized. Please enter your username: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.username = input.readLine();
            chatRoom.addClient(this);
            output.println("To quit type /quit");
            chatRoom.broadcast(username + " has joined the chat!", this);
            boolean flag = true;
            while(flag) {
                String message = input.readLine();
                if(message == null){
                    flag = false;
                    chatRoom.broadcast(username + " has left the chat!", this);
                    chatRoom.removeClient(this);
                }
                else if(message.equalsIgnoreCase("/quit")) {
                    flag = false;
                    chatRoom.broadcast(username + " has left the chat!", this);
                    chatRoom.removeClient(this);
                }
                else {
                    chatRoom.broadcast(username + ": " + message, this);
                }
            }
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }


}