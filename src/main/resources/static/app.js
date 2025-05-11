let tg = window.Telegram.WebApp;

// Инициализация приложения
tg.expand();
tg.enableClosingConfirmation();

// Функция для расчета ИМТ
function calculateBMI() {
    const weight = document.getElementById('weight').value;
    const height = document.getElementById('height').value / 100; // конвертируем см в метры

    if (!weight || !height) {
        showResult('bmiResult', 'Пожалуйста, введите вес и рост');
        return;
    }

    const bmi = weight / (height * height);
    let category;
    let recommendations;

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
        recommendations = "• Обязательно проконсультируйтесь с врачом\n• Создайте дефицит калорий\n• Начните с легких кардио тренировок";
    }

    const result = `Ваш ИМТ: ${bmi.toFixed(1)}\nКатегория: ${category}\n\nРекомендации:\n${recommendations}`;
    showResult('bmiResult', result);
}

// Функция для получения плана тренировок
function getTrainingPlan() {
    const level = document.getElementById('trainingLevel').value;
    let plan;

    switch (level) {
        case 'beginner':
            plan = "План для начинающих:\n\n" +
                   "1. Разминка (10 минут)\n" +
                   "2. Приседания: 3 подхода по 10 повторений\n" +
                   "3. Отжимания: 3 подхода по 5 повторений\n" +
                   "4. Планка: 3 подхода по 20 секунд\n" +
                   "5. Растяжка (10 минут)\n\n" +
                   "Тренируйтесь 3 раза в неделю";
            break;
        case 'intermediate':
            plan = "План для среднего уровня:\n\n" +
                   "1. Разминка (15 минут)\n" +
                   "2. Приседания: 4 подхода по 15 повторений\n" +
                   "3. Отжимания: 4 подхода по 12 повторений\n" +
                   "4. Планка: 4 подхода по 45 секунд\n" +
                   "5. Выпады: 3 подхода по 12 повторений\n" +
                   "6. Растяжка (15 минут)\n\n" +
                   "Тренируйтесь 4 раза в неделю";
            break;
        case 'advanced':
            plan = "План для продвинутых:\n\n" +
                   "1. Разминка (20 минут)\n" +
                   "2. Приседания: 5 подходов по 20 повторений\n" +
                   "3. Отжимания: 5 подходов по 20 повторений\n" +
                   "4. Планка: 5 подходов по 1 минуте\n" +
                   "5. Выпады: 4 подхода по 15 повторений\n" +
                   "6. Берпи: 3 подхода по 10 повторений\n" +
                   "7. Растяжка (20 минут)\n\n" +
                   "Тренируйтесь 5 раз в неделю";
            break;
    }

    showResult('trainingPlan', plan);
}

// Функция для получения плана питания
function getNutritionPlan() {
    const plan = "Рекомендуемые продукты:\n\n" +
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

    showResult('nutritionPlan', plan);
}

// Функция для получения мотивационной цитаты
function getMotivationalQuote() {
    const quotes = [
        "Твой единственный предел - это ты сам.",
        "Каждый день - это новая возможность стать лучше.",
        "Сила не в мышцах, а в силе воли.",
        "Тяжело в тренировке - легко в жизни.",
        "Не останавливайся, когда устал. Останавливайся, когда закончил."
    ];
    const randomQuote = quotes[Math.floor(Math.random() * quotes.length)];
    showResult('motivationalQuote', "💪 " + randomQuote);
}

// Вспомогательная функция для отображения результатов
function showResult(elementId, text) {
    const element = document.getElementById(elementId);
    element.textContent = text;
    element.style.whiteSpace = 'pre-line';
} 