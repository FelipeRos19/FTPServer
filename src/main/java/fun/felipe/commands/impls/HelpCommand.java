package fun.felipe.commands.impls;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fun.felipe.Server;
import fun.felipe.commands.CommandBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HelpCommand extends CommandBase {

    public HelpCommand() {
        super("help", "Mostra a Lista de Comandos e suas Funcionalidades.");
    }

    @Override
    public void execute(JsonObject request, Socket socket) {
        System.out.println("Executando o comando Help!");
        JsonArray commands = new JsonArray();
        for (CommandBase command : Server.getInstance().getCommandController().getRegisteredCommands()) {
            JsonObject commandJson = new JsonObject();
            commandJson.addProperty("name", command.getName());
            commandJson.addProperty("description", command.getDescription());
            commands.add(commandJson);
        }

        JsonObject response = new JsonObject();
        response.addProperty("command", "help");
        response.add("commands", commands);

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            output.write(response.toString());
            output.newLine();
            output.flush();
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
