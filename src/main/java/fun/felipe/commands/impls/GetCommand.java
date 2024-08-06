package fun.felipe.commands.impls;

import com.google.gson.JsonObject;
import fun.felipe.Server;
import fun.felipe.commands.CommandBase;
import fun.felipe.utils.HashUtils;

import java.io.*;
import java.net.Socket;

public class GetCommand extends CommandBase {

    public GetCommand() {
        super("get", "Retorna o Arquivo requisitado pelo Cliente.");
    }

    @Override
    public void execute(JsonObject request, Socket socket) {
        System.out.println("Executando o comando Get!");
        System.out.println(request.toString());
       try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
           String fileName = request.get("file").getAsString();
           File file = new File(Server.getInstance().getConfig().directory_path() + "/" + fileName);

           if (!file.exists()) {
               System.out.println("BAH GURI NÃO ACHEI ESSE ARQUIVITO PRA TU!");
           }

           String fileHash = HashUtils.calculateFileHash(file);

           JsonObject response = new JsonObject();
           response.addProperty("file", fileName);
           response.addProperty("operation", "get");
           response.addProperty("hash", fileHash);
           out.writeUTF(response.toString());

           try (InputStream in = new FileInputStream(file)) {
               byte[] buffer = new byte[4096];
               int bytesRead;
               while ((bytesRead = in.read(buffer)) != -1) {
                   out.write(buffer, 0, bytesRead);
               }
           }
       } catch (IOException exception) {
           throw new RuntimeException(exception);
       }
    }
}
