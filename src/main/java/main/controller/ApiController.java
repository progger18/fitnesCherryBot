package main.controller;

import main.model.UserData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/bmi")
    public String calculateBMI(@RequestBody UserData userData) {
        double height = userData.getHeight() / 100; // конвертируем см в метры
        double bmi = userData.getWeight() / (height * height);
        String category;
        String recommendations;

        if (bmi < 18.5) {
            category = "Недостаточный вес";
            recommendations = "• Увеличьте калорийность рациона\n• Добавьте силовые тренировки\n• Питайтесь чаще";
        } else if (bmi < 25) {
            category = "Нормальный вес";
            recommendations = "• Поддерживайте текущий режим\n• Регулярно тренируйтесь\n• Сбалансированно питайтесь";
        } else {
            category = "Избыточный вес";
            recommendations = "• Создайте дефицит калорий\n• Добавьте кардио тренировки\n• Уменьшите потребление простых углеводов";
        }

        return String.format("Ваш ИМТ: %.1f\nКатегория: %s\n\nРекомендации:\n%s", bmi, category, recommendations);
    }

    @GetMapping("/training/{level}")
    public String getTrainingPlan(@PathVariable String level) {
        switch (level) {
            case "beginner":
                return "План для начинающих:\n\n" +
                       "1. Разминка (10 минут)\n" +
                       "2. Приседания: 3 подхода по 10 повторений\n" +
                       "3. Отжимания: 3 подхода по 5 повторений\n" +
                       "4. Планка: 3 подхода по 20 секунд\n" +
                       "5. Растяжка (10 минут)\n\n" +
                       "Тренируйтесь 3 раза в неделю";
            case "intermediate":
                return "План для среднего уровня:\n\n" +
                       "1. Разминка (15 минут)\n" +
                       "2. Приседания: 4 подхода по 15 повторений\n" +
                       "3. Отжимания: 4 подхода по 12 повторений\n" +
                       "4. Планка: 4 подхода по 45 секунд\n" +
                       "5. Выпады: 3 подхода по 12 повторений\n" +
                       "6. Растяжка (15 минут)\n\n" +
                       "Тренируйтесь 4 раза в неделю";
            case "advanced":
                return "План для продвинутых:\n\n" +
                       "1. Разминка (20 минут)\n" +
                       "2. Приседания со штангой: 5 подходов по 8 повторений\n" +
                       "3. Жим лежа: 5 подходов по 8 повторений\n" +
                       "4. Становая тяга: 5 подходов по 6 повторений\n" +
                       "5. Подтягивания: 4 подхода по 10 повторений\n" +
                       "6. Планка: 5 подходов по 60 секунд\n" +
                       "7. Растяжка (20 минут)\n\n" +
                       "Тренируйтесь 5 раз в неделю";
            default:
                return "Неверный уровень подготовки";
        }
    }

    @GetMapping("/nutrition")
    public String getNutritionPlan() {
        return "Рекомендуемые продукты:\n\n" +
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
    }

    @GetMapping("/motivation")
    public String getMotivationalQuote() {
        String[] quotes = {
            "Твой единственный предел - это ты сам.",
            "Каждый день - это новая возможность стать лучше.",
            "Сила не в мышцах, а в силе воли.",
            "Тяжело в тренировке - легко в жизни.",
            "Не останавливайся, когда устал. Останавливайся, когда закончил."
        };
        return "💪 " + quotes[(int) (Math.random() * quotes.length)];
    }
} 