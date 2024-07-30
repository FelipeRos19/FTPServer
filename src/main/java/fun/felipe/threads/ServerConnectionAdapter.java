package fun.felipe.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionAdapter {
    private final int port;

    public ServerConnectionAdapter(int port) {
        this.port = port;
        this.connectionHandler();
    }

    private void connectionHandler() {
        System.out.println("Iniciando Servidor na Porta: " + this.port);
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientConnectionHandler(socket);
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
