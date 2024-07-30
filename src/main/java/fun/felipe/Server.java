package fun.felipe;

import fun.felipe.commands.impls.GetCommand;
import fun.felipe.commands.impls.HelpCommand;
import fun.felipe.commands.impls.ListCommand;
import fun.felipe.controllers.CommandController;
import fun.felipe.data.Config;
import fun.felipe.routines.SetupRoutine;
import fun.felipe.threads.ServerConnectionAdapter;


public class Server {
    private static Server instance;
    private final Config config;
    private final CommandController commandController;

    public Server() {
        this.config = new SetupRoutine().init();
        this.commandController = new CommandController();
        this.register();
        System.out.println("Iniciando o Servidor...");
    }

    public static void main(String[] args) {
        instance = new Server();
        new ServerConnectionAdapter(instance.getConfig().port());
    }

    private void register() {
        commandController.addCommand(new ListCommand());
        commandController.addCommand(new HelpCommand());
        commandController.addCommand(new GetCommand());
    }

    public static Server getInstance() {
        return instance;
    }

    public Config getConfig() {
        return config;
    }

    public CommandController getCommandController() {
        return commandController;
    }
}