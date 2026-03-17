

// Server

ServerSocket serverSocket = new ServerSocket(5000);
while (true) {
    Socket clientSocket = serverSocket.accept();
    ClientHandler handler = new ClientHandler(clientSocket, chatRoom);
    new Thread(handler).start();
}