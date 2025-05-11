package main.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class BotConfig {
    private static final String CONFIG_FILE = "config.properties";
    private static Configuration config;

    static {
        try {
            Configurations configs = new Configurations();
            config = configs.properties(new File("src/main/resources/" + CONFIG_FILE));
        } catch (ConfigurationException e) {
            throw new RuntimeException("Ошибка загрузки конфигурации", e);
        }
    }

    public static String getBotToken() {
        return config.getString("bot.token");
    }

    public static String getBotUsername() {
        return config.getString("bot.username");
    }
} 