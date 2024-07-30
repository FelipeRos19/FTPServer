package fun.felipe.commands.impls;

import com.google.gson.JsonObject;
import fun.felipe.Server;
import fun.felipe.commands.CommandBase;
import fun.felipe.utils.HashUtils;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class GetCommand extends CommandBase {

    public GetCommand() {
        super("get", "Retorna o Arquivo requisitado pelo Cliente.");
    }

    @Override
    public void execute(JsonObject request, Socket socket) {
        String fileName = request.get("file_name").getAsString();
        File file = new File(Server.getInstance().getConfig().directory_path(), fileName);
        boolean error = false;

        if (!file.exists()) {
            JsonObject responseError = new JsonObject();
            responseError.addProperty("file", fileName);
            responseError.addProperty("operatiion", "get");
            responseError.addProperty("status", "fail");
            responseError.addProperty("message", "File not found.");

            /*
            try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                output.write(responseError.toString());
                output.newLine();
                output.flush();
            } catch (IOException exception) {
                exception.printStackTrace(System.err);
            }
             */
        }

        String fileHash = HashUtils.calculateFileHash(file);
        JsonObject response = new JsonObject();
        response.addProperty("file", fileName);
        response.addProperty("operation", "get");
        response.addProperty("hash", fileHash);


    }
}
