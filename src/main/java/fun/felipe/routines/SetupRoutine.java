package fun.felipe.routines;

import com.google.gson.Gson;
import fun.felipe.data.Config;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SetupRoutine {

    public Config init() {
        File root = new File("./");
        Config config;

        List<File> rootFiles = Arrays.asList(Objects.requireNonNull(root.listFiles()));
        if (rootFiles.isEmpty()) throw new RuntimeException("Não foi possível acessar os arquivos!");

        boolean hasConfig = false;
        File configFile = null;

        for (File file : rootFiles) {
            if (file.getName().equals("config.json")) {
                hasConfig = true;
                configFile = file;
                break;
            }
        }

        if (!hasConfig) {
            System.out.println("Não foi encontrada nenhuma Configuração!");
            config = this.loadConfig("src/main/resources/config.json");

        } else {
            config = this.loadConfig(configFile.getPath());
        }

        this.createDirectory(config.directory_path());

        System.out.println("Configurações Definidas:");
        System.out.println("Endereço do Servidor: " + config.address());
        System.out.println("Porta do Servidor: " + config.port());
        System.out.println("Diretório de Armazenamento: " + config.directory_path());

        return config;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        if (file.exists()) return;
        if(!file.mkdirs()) throw new RuntimeException("Não foi possível criar o Diretório de Armazenamento!");
    }

    private Config loadConfig(String path) {
        try {
           return new Gson().fromJson(new FileReader(path), Config.class);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
