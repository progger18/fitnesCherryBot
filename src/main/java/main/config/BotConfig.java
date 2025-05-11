package main.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class BotConfig {
    private static String botToken;
    private static String botUsername;
    private static String webAppUrl;

    static {
        // Сначала пытаемся получить значения из переменных окружения
        botToken = System.getenv("BOT_TOKEN");
        botUsername = System.getenv("BOT_USERNAME");
        webAppUrl = System.getenv("WEBAPP_URL");

        // Если переменные окружения не установлены, читаем из файла конфигурации
        if (botToken == null || botUsername == null || webAppUrl == null) {
            try {
                Configurations configs = new Configurations();
                Configuration config = configs.properties(new File("src/main/resources/config.properties"));
                
                botToken = botToken != null ? botToken : config.getString("bot.token");
                botUsername = botUsername != null ? botUsername : config.getString("bot.username");
                webAppUrl = webAppUrl != null ? webAppUrl : config.getString("webapp.url");
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getBotToken() {
        return botToken;
    }

    public static String getBotUsername() {
        return botUsername;
    }

    public static String getWebAppUrl() {
        return webAppUrl;
    }
} 