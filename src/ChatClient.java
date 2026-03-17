import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class ChatClient {
    private Socket bob;
    private BufferedReader input;
    private PrintWriter output;
    
    public ChatClient(String host, int port) throws IOException {
        this.bob = new Socket(host, port);
        this.input = new BufferedReader(new InputStreamReader(bob.getInputStream()));
        this.output = new PrintWriter(bob.getOutputStream(), true);
    }

    public void reading() {
        new Thread(() -> {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.print("\r" + message + "\n> ");
                }
                System.out.println("Connection closed by server.");
                this.bob.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void writing() {
        new Thread(() -> {
            try {
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while ((message = consoleInput.readLine()) != null) {
                    output.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        try {
            ChatClient client = new ChatClient(host, port);
            client.reading();
            client.writing();
            System.out.println("> ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
}