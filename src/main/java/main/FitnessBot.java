package main;

import lombok.extern.slf4j.Slf4j;
import main.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FitnessBot extends TelegramLongPollingBot {
    private static final String WEBAPP_URL = "https://your-domain.com"; // Замените на ваш домен

    @Override
    public String getBotUsername() {
        return BotConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                sendStartMessage(chatId);
            }
        }
    }

    private void sendStartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать в Fitness Assistant Bot! 🏋️‍♂️\n\n" +
                "Нажмите кнопку ниже, чтобы открыть приложение:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Открыть приложение");
        button.setWebApp(new org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo(WEBAPP_URL));
        row.add(button);
        
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending start message", e);
        }
    }

    private void calculateBMI(long chatId, String messageText) {
        String[] parts = messageText.split("\\s+");
        double weight = Double.parseDouble(parts[0]);
        double height = Double.parseDouble(parts[1]) / 100; // конвертируем см в метры

        double bmi = weight / (height * height);
        String category;
        if (bmi < 18.5) {
            category = "Недостаточный вес";
        } else if (bmi < 25) {
            category = "Нормальный вес";
        } else if (bmi < 30) {
            category = "Избыточный вес";
        } else {
            category = "Ожирение";
        }

        String response = String.format("Ваш ИМТ: %.1f\nКатегория: %s\n\n", bmi, category);
        response += "Рекомендации:\n";
        if (bmi < 18.5) {
            response += "• Увеличьте калорийность рациона\n• Добавьте силовые тренировки\n• Питайтесь чаще";
        } else if (bmi < 25) {
            response += "• Поддерживайте текущий режим\n• Регулярно тренируйтесь\n• Сбалансированно питайтесь";
        } else {
            response += "• Создайте дефицит калорий\n• Добавьте кардио тренировки\n• Уменьшите потребление простых углеводов";
        }

        sendMessage(chatId, response);
    }

    private void sendTrainingPlan(long chatId) {
        String message = "Выберите ваш уровень подготовки:\n\n" +
                "1️⃣ Начинающий\n" +
                "2️⃣ Средний\n" +
                "3️⃣ Продвинутый\n\n" +
                "Отправьте цифру для получения плана тренировок";
        sendMessage(chatId, message);
    }

    private void sendNutritionInfo(long chatId) {
        String message = "Рекомендуемые продукты:\n\n" +
                "🥩 Белки:\n" +
                "• Куриная грудка\n" +
                "• Рыба (лосось, тунец)\n" +
                "• Яйца\n" +
                "• Творог\n\n" +
                "🌾 Углеводы:\n" +
                "• Коричневый рис\n" +
                "• Овсянка\n" +
                "• Киноа\n" +
                "• Сладкий картофель\n\n" +
                "🥑 Жиры:\n" +
                "• Авокадо\n" +
                "• Орехи\n" +
                "• Оливковое масло\n" +
                "• Жирная рыба\n\n" +
                "🥬 Овощи:\n" +
                "• Брокколи\n" +
                "• Шпинат\n" +
                "• Болгарский перец\n" +
                "• Цукини";
        sendMessage(chatId, message);
    }

    private void sendMotivationalQuote(long chatId) {
        String[] quotes = {
            "Твой единственный предел - это ты сам.",
            "Каждый день - это новая возможность стать лучше.",
            "Сила не в мышцах, а в силе воли.",
            "Тяжело в тренировке - легко в жизни.",
            "Не останавливайся, когда устал. Останавливайся, когда закончил."
        };
        String randomQuote = quotes[(int) (Math.random() * quotes.length)];
        sendMessage(chatId, "💪 " + randomQuote);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }
} 