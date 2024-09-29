package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ScratchGameService scratchGameService = new ScratchGameService();

        String configFilePath = null;
        int bettingAmount = 0;

        // Обработка аргументов
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    configFilePath = args[++i];
                    break;
                case "--betting-amount":
                    bettingAmount = Integer.parseInt(args[++i]);
                    break;
                default:
                    System.out.println("Unknown argument: " + args[i]);
                    break;
            }
        }

        if (configFilePath == null || bettingAmount == 0) {
            System.out.println("Usage: java -jar <your-jar-file> --config <config-file> --betting-amount <amount>");
            System.exit(1);
        }

        // Чтение конфигурационного файла
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configFilePath), Config.class);

        scratchGameService.playGame(config, String.valueOf(bettingAmount));

    }
}
