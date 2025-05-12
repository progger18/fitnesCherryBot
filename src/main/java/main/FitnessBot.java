package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class FitnessBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botUsername;
    private final String webAppUrl;

    public FitnessBot(String botToken, String botUsername, String webAppUrl) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.webAppUrl = webAppUrl;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendWelcomeMessage(chatId);
                    break;
                case "/help":
                    sendHelpMessage(chatId);
                    break;
                case "🌐 Открыть приложение":
                    sendWebAppButton(chatId);
                    break;
                case "💪 План тренировок":
                    sendTrainingPlan(chatId);
                    break;
                case "🥗 План питания":
                    sendNutritionPlan(chatId);
                    break;
                case "📊 Калькулятор ИМТ":
                    sendBMICalculator(chatId);
                    break;
                case "🔥 Мотивация":
                    sendMotivationalQuote(chatId);
                    break;
                default:
                    sendDefaultMessage(chatId);
            }
        }
    }

    private void sendWelcomeMessage(long chatId) {
        String welcomeText = "👋 Привет! Я твой фитнес-ассистент.\n\n" +
                "Я помогу тебе:\n" +
                "• Рассчитать ИМТ и получить рекомендации\n" +
                "• Создать персональный план тренировок\n" +
                "• Составить план питания\n" +
                "• Поддерживать мотивацию\n\n" +
                "Используй команды меню или нажми кнопку ниже, чтобы открыть веб-приложение.";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(welcomeText);
        message.setReplyMarkup(createMainMenuKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendHelpMessage(long chatId) {
        String helpText = "📋 Доступные команды:\n\n" +
                "/start - Начать работу с ботом\n" +
                "/help - Показать это сообщение\n\n" +
                "🌐 Открыть приложение - Открыть веб-интерфейс\n" +
                "💪 План тренировок - Получить план тренировок\n" +
                "🥗 План питания - Получить план питания\n" +
                "📊 Калькулятор ИМТ - Рассчитать ИМТ\n" +
                "🔥 Мотивация - Получить мотивационную цитату";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(helpText);
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendWebAppButton(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Нажмите кнопку ниже, чтобы открыть веб-приложение:");
        message.setReplyMarkup(createWebAppKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTrainingPlan(long chatId) {
        String trainingText = "Выберите ваш уровень подготовки:\n\n" +
                "1️⃣ Начинающий\n" +
                "2️⃣ Средний\n" +
                "3️⃣ Продвинутый\n\n" +
                "Для получения подробного плана тренировок, пожалуйста, используйте веб-приложение.";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(trainingText);
        message.setReplyMarkup(createWebAppKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNutritionPlan(long chatId) {
        String nutritionText = "Для получения подробного плана питания с калорийностью и макронутриентами, " +
                "пожалуйста, используйте веб-приложение.";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(nutritionText);
        message.setReplyMarkup(createWebAppKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendBMICalculator(long chatId) {
        String bmiText = "Для расчета ИМТ и получения персональных рекомендаций, " +
                "пожалуйста, используйте веб-приложение.";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(bmiText);
        message.setReplyMarkup(createWebAppKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMotivationalQuote(long chatId) {
        String[] quotes = {
            "💪 Твой единственный предел - это ты сам.",
            "🌟 Каждый день - это новая возможность стать лучше.",
            "💫 Сила не в мышцах, а в силе воли.",
            "🔥 Тяжело в тренировке - легко в жизни.",
            "⚡️ Не останавливайся, когда устал. Останавливайся, когда закончил.",
            "💪 Твое тело может вынести почти все. Твоя задача - убедить в этом свой разум.",
            "✨ Успех - это сумма небольших усилий, повторяемых изо дня в день.",
            "🎯 Если это не бросает тебе вызов, это не меняет тебя.",
            "💫 Тренировка - это не наказание, а награда за то, что ты можешь это делать.",
            "🌟 Твое будущее создается тем, что ты делаешь сегодня.",
            "💪 Не бойся быть сильным. Кто-то где-то в это время тренируется, чтобы победить тебя.",
            "🔥 Боль, которую ты чувствуешь сегодня, станет силой, которую ты почувствуешь завтра.",
            "💫 Тренировка - это не просто физическая активность, это образ жизни.",
            "⚡️ Твое тело может все. Нужно только убедить в этом свой разум.",
            "🌟 Каждая тренировка - это шаг к лучшей версии себя."
        };
        
        String randomQuote = quotes[(int) (Math.random() * quotes.length)];

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(randomQuote);
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDefaultMessage(long chatId) {
        String defaultText = "Пожалуйста, используйте команды меню или веб-приложение для взаимодействия с ботом.";
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(defaultText);
        message.setReplyMarkup(createMainMenuKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createMainMenuKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("🌐 Открыть приложение");
        webAppButton.setWebApp(new org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo(webAppUrl));
        row1.add(webAppButton);
        
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton trainingButton = new InlineKeyboardButton();
        trainingButton.setText("💪 План тренировок");
        trainingButton.setCallbackData("training");
        row2.add(trainingButton);
        
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton nutritionButton = new InlineKeyboardButton();
        nutritionButton.setText("🥗 План питания");
        nutritionButton.setCallbackData("nutrition");
        row3.add(nutritionButton);
        
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        InlineKeyboardButton bmiButton = new InlineKeyboardButton();
        bmiButton.setText("📊 Калькулятор ИМТ");
        bmiButton.setCallbackData("bmi");
        row4.add(bmiButton);
        
        List<InlineKeyboardButton> row5 = new ArrayList<>();
        InlineKeyboardButton motivationButton = new InlineKeyboardButton();
        motivationButton.setText("🔥 Мотивация");
        motivationButton.setCallbackData("motivation");
        row5.add(motivationButton);
        
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        
        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardMarkup createWebAppKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("🌐 Открыть приложение");
        webAppButton.setWebApp(new org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo(webAppUrl));
        row.add(webAppButton);
        
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
} 