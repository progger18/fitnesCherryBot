package main;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр API
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Создаем экземпляр бота
            FitnessBot bot = new FitnessBot(
                "YOUR_BOT_TOKEN",
                "FitnessCherryBot",
                "https://fitnescherrybot.netlify.app"
            );

            // Регистрируем бота
            botsApi.registerBot(bot);
            
            System.out.println("Бот успешно запущен!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}