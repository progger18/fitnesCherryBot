package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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
                    SendMessage webAppMessage = new SendMessage();
                    webAppMessage.setChatId(chatId);
                    webAppMessage.setText("Нажмите кнопку ниже, чтобы открыть веб-приложение:");
                    webAppMessage.setReplyMarkup(createWebAppKeyboard());
                    try {
                        execute(webAppMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "💪 План тренировок":
                    sendTrainingLevelKeyboard(chatId);
                    break;
                case "🥗 План питания":
                    sendNutritionPlan(chatId);
                    break;
                case "📊 Калькулятор ИМТ":
                    sendBMICalculatorInstructions(chatId);
                    break;
                case "🔥 Мотивация":
                    sendMotivationalQuote(chatId);
                    break;
                default:
                    if (messageText.matches("\\d+\\s+\\d+")) {
                        calculateBMI(chatId, messageText);
                    } else if (messageText.matches("[1-3]")) {
                        sendTrainingPlan(chatId, messageText);
                    } else {
                        sendDefaultMessage(chatId);
                    }
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
                "Выбери нужный пункт меню или используй команды:\n" +
                "/start - Начать работу\n" +
                "/help - Показать справку";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(welcomeText);
        message.setReplyMarkup(createMainKeyboard());
        
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
                "Как пользоваться ботом:\n\n" +
                "1. Расчет ИМТ:\n" +
                "   • Нажмите '📊 Калькулятор ИМТ'\n" +
                "   • Введите вес и рост через пробел (например: 70 175)\n\n" +
                "2. План тренировок:\n" +
                "   • Нажмите '💪 План тренировок'\n" +
                "   • Выберите уровень подготовки (1-3)\n\n" +
                "3. План питания:\n" +
                "   • Нажмите '🥗 План питания'\n" +
                "   • Получите рекомендации по питанию\n\n" +
                "4. Мотивация:\n" +
                "   • Нажмите '🔥 Мотивация'\n" +
                "   • Получите мотивационную цитату";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(helpText);
        message.setReplyMarkup(createMainKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTrainingLevelKeyboard(long chatId) {
        String text = "Выберите ваш уровень подготовки:\n\n" +
                "1️⃣ Начинающий\n" +
                "2️⃣ Средний\n" +
                "3️⃣ Продвинутый\n\n" +
                "Отправьте цифру (1-3) для получения плана тренировок.";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTrainingPlan(long chatId, String level) {
        String plan;
        switch (level) {
            case "1":
                plan = "🏋️‍♂️ План для начинающих:\n\n" +
                        "Понедельник (Ноги и ягодицы):\n" +
                        "1. Приседания: 3 подхода по 10 повторений\n" +
                        "2. Выпады: 3 подхода по 8 повторений на каждую ногу\n" +
                        "3. Подъемы на носки: 3 подхода по 15 повторений\n" +
                        "4. Планка: 3 подхода по 20 секунд\n\n" +
                        "Среда (Верхняя часть тела):\n" +
                        "1. Отжимания: 3 подхода по 5 повторений\n" +
                        "2. Тяга гантелей: 3 подхода по 10 повторений\n" +
                        "3. Подъемы рук: 3 подхода по 12 повторений\n" +
                        "4. Планка: 3 подхода по 20 секунд\n\n" +
                        "Пятница (Кардио и кор):\n" +
                        "1. Прыжки на скакалке: 3 подхода по 1 минуте\n" +
                        "2. Берпи: 3 подхода по 5 повторений\n" +
                        "3. Скручивания: 3 подхода по 12 повторений\n" +
                        "4. Планка: 3 подхода по 20 секунд\n\n" +
                        "Рекомендации:\n" +
                        "• Отдых между подходами: 60-90 секунд\n" +
                        "• Пейте воду во время тренировки\n" +
                        "• Следите за техникой выполнения";
                break;
            case "2":
                plan = "🏋️‍♂️ План для среднего уровня:\n\n" +
                        "Понедельник (Ноги):\n" +
                        "1. Приседания со штангой: 4 подхода по 12 повторений\n" +
                        "2. Выпады с гантелями: 4 подхода по 10 повторений\n" +
                        "3. Жим ногами: 4 подхода по 12 повторений\n" +
                        "4. Подъемы на носки: 4 подхода по 15 повторений\n\n" +
                        "Среда (Грудь и трицепс):\n" +
                        "1. Жим штанги лежа: 4 подхода по 10 повторений\n" +
                        "2. Разведение гантелей: 4 подхода по 12 повторений\n" +
                        "3. Отжимания на брусьях: 4 подхода по 10 повторений\n" +
                        "4. Разгибания рук: 4 подхода по 12 повторений\n\n" +
                        "Пятница (Спина и бицепс):\n" +
                        "1. Подтягивания: 4 подхода по 8 повторений\n" +
                        "2. Тяга верхнего блока: 4 подхода по 12 повторений\n" +
                        "3. Тяга гантели в наклоне: 4 подхода по 10 повторений\n" +
                        "4. Сгибания рук: 4 подхода по 12 повторений\n\n" +
                        "Рекомендации:\n" +
                        "• Отдых между подходами: 45-60 секунд\n" +
                        "• Добавьте кардио 2-3 раза в неделю\n" +
                        "• Следите за прогрессом";
                break;
            case "3":
                plan = "🏋️‍♂️ План для продвинутых:\n\n" +
                        "Понедельник (Ноги):\n" +
                        "1. Приседания со штангой: 5 подходов по 8 повторений\n" +
                        "2. Румынская тяга: 5 подходов по 8 повторений\n" +
                        "3. Выпады с гантелями: 4 подхода по 10 повторений\n" +
                        "4. Жим ногами: 4 подхода по 10 повторений\n\n" +
                        "Вторник (Грудь и трицепс):\n" +
                        "1. Жим штанги лежа: 5 подходов по 6-8 повторений\n" +
                        "2. Жим гантелей на наклонной скамье: 4 подхода по 8 повторений\n" +
                        "3. Разведение гантелей: 4 подхода по 12 повторений\n" +
                        "4. Отжимания на брусьях: 4 подхода по 10 повторений\n\n" +
                        "Четверг (Спина и бицепс):\n" +
                        "1. Становая тяга: 5 подходов по 6 повторений\n" +
                        "2. Подтягивания: 4 подхода по 8-10 повторений\n" +
                        "3. Тяга верхнего блока: 4 подхода по 10 повторений\n" +
                        "4. Тяга гантели в наклоне: 4 подхода по 10 повторений\n\n" +
                        "Пятница (Плечи и руки):\n" +
                        "1. Жим штанги над головой: 5 подходов по 8 повторений\n" +
                        "2. Разведение гантелей: 4 подхода по 12 повторений\n" +
                        "3. Тяга к подбородку: 4 подхода по 10 повторений\n" +
                        "4. Сгибания рук: 4 подхода по 10 повторений\n\n" +
                        "Рекомендации:\n" +
                        "• Отдых между подходами: 30-45 секунд\n" +
                        "• Используйте дроп-сеты и суперсеты\n" +
                        "• Добавьте кардио 3-4 раза в неделю";
                break;
            default:
                plan = "Пожалуйста, выберите уровень подготовки (1-3)";
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(plan);
        message.setReplyMarkup(createMainKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNutritionPlan(long chatId) {
        String nutritionText = "🥗 Рекомендации по питанию:\n\n" +
                "🥩 Белки:\n" +
                "• Куриная грудка (100г) - 165 ккал, 31г белка\n" +
                "• Лосось (100г) - 208 ккал, 22г белка\n" +
                "• Яйца (1 шт) - 70 ккал, 6г белка\n" +
                "• Творог 5% (100г) - 121 ккал, 17г белка\n\n" +
                "🌾 Углеводы:\n" +
                "• Коричневый рис (100г) - 112 ккал\n" +
                "• Овсянка (100г) - 68 ккал\n" +
                "• Киноа (100г) - 120 ккал\n" +
                "• Сладкий картофель (100г) - 86 ккал\n\n" +
                "🥑 Жиры:\n" +
                "• Авокадо (100г) - 160 ккал\n" +
                "• Миндаль (100г) - 576 ккал\n" +
                "• Оливковое масло (1 ст.л) - 120 ккал\n" +
                "• Жирная рыба (100г) - 208 ккал\n\n" +
                "🥬 Овощи:\n" +
                "• Брокколи (100г) - 34 ккал\n" +
                "• Шпинат (100г) - 23 ккал\n" +
                "• Болгарский перец (100г) - 31 ккал\n" +
                "• Цукини (100г) - 17 ккал\n\n" +
                "Рекомендации:\n" +
                "• Белки: 1.6-2.2г на кг веса\n" +
                "• Жиры: 0.8-1г на кг веса\n" +
                "• Углеводы: 3-5г на кг веса\n" +
                "• Пейте 2-3 литра воды в день\n" +
                "• Питайтесь 4-6 раз в день";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(nutritionText);
        message.setReplyMarkup(createMainKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendBMICalculatorInstructions(long chatId) {
        String text = "📊 Калькулятор ИМТ\n\n" +
                "Введите ваш вес в кг и рост в см через пробел.\n" +
                "Например: 70 175";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void calculateBMI(long chatId, String messageText) {
        String[] parts = messageText.split("\\s+");
        double weight = Double.parseDouble(parts[0]);
        double height = Double.parseDouble(parts[1]) / 100; // конвертируем см в метры

        double bmi = weight / (height * height);
        String category;
        String recommendations;

        if (bmi < 18.5) {
            category = "Недостаточный вес";
            recommendations = "• Увеличьте калорийность рациона\n• Добавьте силовые тренировки\n• Питайтесь чаще";
        } else if (bmi < 25) {
            category = "Нормальный вес";
            recommendations = "• Поддерживайте текущий режим\n• Регулярно тренируйтесь\n• Сбалансированно питайтесь";
        } else if (bmi < 30) {
            category = "Избыточный вес";
            recommendations = "• Создайте дефицит калорий\n• Добавьте кардио тренировки\n• Уменьшите потребление простых углеводов";
        } else {
            category = "Ожирение";
            recommendations = "• Обязательно проконсультируйтесь с врачом\n• Начните с легких кардио тренировок\n• Строго следите за питанием";
        }

        String response = String.format("📊 Результаты расчета ИМТ:\n\n" +
                "Ваш ИМТ: %.1f\n" +
                "Категория: %s\n\n" +
                "Рекомендации:\n%s", bmi, category, recommendations);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);
        message.setReplyMarkup(createMainKeyboard());
        
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
        message.setReplyMarkup(createMainKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDefaultMessage(long chatId) {
        String defaultText = "Пожалуйста, используйте кнопки меню для навигации.";
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(defaultText);
        message.setReplyMarkup(createMainKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup createMainKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        
        KeyboardRow row1 = new KeyboardRow();
        row1.add("💪 План тренировок");
        row1.add("🥗 План питания");
        
        KeyboardRow row2 = new KeyboardRow();
        row2.add("📊 Калькулятор ИМТ");
        row2.add("🔥 Мотивация");
        
        KeyboardRow row3 = new KeyboardRow();
        row3.add("🌐 Открыть приложение");
        
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
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