package fun.felipe.threads;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.Server;

import java.io.*;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private final Socket socket;

    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
        this.run();
    }

    @Override
    public void run() {
        System.out.println("Estabelecendo Conexão com o endereço: " + this.socket.getInetAddress().getHostAddress());
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String request = input.readLine();
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            String command = jsonRequest.get("command").getAsString();

            Server.getInstance().getCommandController().commandProcess(command, jsonRequest, this.socket);
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
