package fun.felipe.commands;

import com.google.gson.JsonObject;

import java.net.Socket;

public abstract class CommandBase {
    private final String commandName;
    private final String commandDescription;

    public CommandBase(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    public String getName() {
        return this.commandName;
    }

    public String getDescription() {
        return this.commandDescription;
    }

    public abstract void execute(JsonObject request, Socket socket);
}
