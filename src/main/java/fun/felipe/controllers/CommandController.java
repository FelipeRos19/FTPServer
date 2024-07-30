package fun.felipe.controllers;

import com.google.gson.JsonObject;
import fun.felipe.commands.CommandBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandController {
    private final List<CommandBase> registeredCommands;

    public CommandController() {
        this.registeredCommands = new ArrayList<>();
    }

    public void addCommand(CommandBase command) {
        this.registeredCommands.add(command);
    }

    public List<CommandBase> getRegisteredCommands() {
        return registeredCommands;
    }

    public void commandProcess(String commandName, JsonObject request, Socket socket) {
        System.out.println("Processador de Comandos foi chamado para tentar executar o Comando: " + commandName);
        for (CommandBase command : registeredCommands) {
            if (command.getName().equals(commandName)) {
                command.execute(request, socket);
                return;
            }
        }

        System.out.println("O Comando " + commandName + " não foi encontrado!");
        JsonObject error = new JsonObject();
        error.addProperty("error", "Command not found");
        this.sendError(error, socket);
    }

    private void sendError(JsonObject error, Socket socket) {
        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            output.write(error.toString());
            output.newLine();
            output.flush();
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
