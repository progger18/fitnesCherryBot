package main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Component;

@Component
public class BotConfig {
    private String botToken;
    private String botUsername;

    @Value("${bot.token}")
    public void setBotToken(String token) {
        this.botToken = token;
    }

    @Value("${bot.username}")
    public void setBotUsername(String username) {
        this.botUsername = username;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botUsername;
    }
} 