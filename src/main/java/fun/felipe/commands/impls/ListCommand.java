package fun.felipe.commands.impls;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fun.felipe.Server;
import fun.felipe.commands.CommandBase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ListCommand extends CommandBase {

    public ListCommand() {
        super("list", "Lista Todos os Arquivos no Servidor.");
    }

    @Override
    public void execute(JsonObject request, Socket socket) {
        System.out.println("Executando o comando List!");
        JsonArray fileList = new JsonArray();

        String storagePath = Server.getInstance().getConfig().directory_path();

        File[] files = new File(storagePath).listFiles();
        for (File file : files) {
            JsonObject fileObject = new JsonObject();
            fileObject.addProperty("name", file.getName());
            fileList.add(fileObject);
        }

        JsonObject response = new JsonObject();
        response.addProperty("command", "List");
        response.add("files", fileList);

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            output.write(response.toString());
            output.newLine();
            output.flush();
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
