package main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {
    private static String botToken;
    private static String botUsername;
    private static String webAppUrl;

    @Value("${bot.token}")
    public void setBotToken(String token) {
        botToken = token;
    }

    @Value("${bot.username}")
    public void setBotUsername(String username) {
        botUsername = username;
    }

    @Value("${webapp.url}")
    public void setWebAppUrl(String url) {
        webAppUrl = url;
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