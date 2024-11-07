package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AliasGame {

    private List<String> words;  // Список слов для игры
    private int currentWordIndex;  // Индекс текущего слова
    private long chatId;  // ID чата пользователя
    private int score;  // Баллы пользователя
    private int round;  // Номер текущего раунда
    private Timer timer;  // Таймер для раунда
    private static final int MAX_ROUNDS = 3;
    private static final int ROUND_TIME = 30000;  // 30 секунд
    private boolean gameOver;  // Статус завершения игры
    private boolean awaitingConfirmation;  // Флаг ожидания подтверждения

    public AliasGame(long chatId) {
        this.chatId = chatId;
        this.words = new ArrayList<>();
        this.currentWordIndex = 0;
        this.score = 0;
        this.round = 1;
        this.gameOver = false;
        this.awaitingConfirmation = false;
        initializeWords();  // Заполняем список слов для игры
        startTimer();  // Запускаем таймер на 30 секунд для раунда
    }

    // Инициализация списка слов (50 айтишных слов)
    private void initializeWords() {
        words.add("Программирование");
        words.add("Алгоритм");
        words.add("Дебаг");
        words.add("Синтаксис");
        words.add("Компилятор");
        words.add("Сервер");
        words.add("База данных");
        words.add("Кэш");
        words.add("Виртуализация");
        words.add("API");
        words.add("Архитектура");
        words.add("Платформа");
        words.add("Операционная система");
        words.add("Блокчейн");
        words.add("Git");
        words.add("Массив");
        words.add("Классы");
        words.add("Объект");
        words.add("Инкапсуляция");
        words.add("Наследование");
        words.add("Полиморфизм");
        words.add("Сетевой протокол");
        words.add("HTTP");
        words.add("REST");
        words.add("Web-сервис");
        words.add("Фреймворк");
        words.add("Node.js");
        words.add("Python");
        words.add("Java");
        words.add("C++");
        words.add("Kotlin");
        words.add("JavaScript");
        words.add("Библиотека");
        words.add("IDE");
        words.add("Контейнеризация");
        words.add("Docker");
        words.add("Kubernetes");
        words.add("Микросервисы");
        words.add("Cloud");
        words.add("SaaS");
        words.add("DevOps");
        words.add("CI/CD");
        words.add("Unit-тест");
        words.add("Технологии");
        words.add("Мобильное приложение");
        words.add("React");
        words.add("Vue.js");
        words.add("TypeScript");
        words.add("Шаблон проектирования");
        words.add("Data Science");
    }

    // Начало игры (при старте)
    public String startGame() {
        if (currentWordIndex < words.size()) {
            return "Объясните слово: " + words.get(currentWordIndex);
        } else {
            return "Игра завершена. Все слова были объяснены!";
        }
    }

    // Переход к следующему слову
    public String nextWord(boolean isCorrect) {
        if (isCorrect) {
            score++;  // Добавляем балл за правильный ответ
        }

        if (currentWordIndex + 1 < words.size()) {
            currentWordIndex++;
            return "Объясните слово: " + words.get(currentWordIndex);
        } else {
            return "Игра завершена. Все слова были объяснены!";
        }
    }

    // Пропуск текущего слова
    public String skipWord() {
        if (currentWordIndex + 1 < words.size()) {
            currentWordIndex++;
            return "Слово пропущено. Объясните следующее слово: " + words.get(currentWordIndex);
        } else {
            return "Игра завершена. Все слова были объяснены!";
        }
    }

    // Запуск таймера на 30 секунд
    private void startTimer() {
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endRound();
            }
        }, ROUND_TIME);  // 30 секунд
    }

    // Завершение раунда и подведение итогов
    public void endRound() {
        if (round < MAX_ROUNDS) {
            awaitingConfirmation = true;  // Ожидаем подтверждения игрока для продолжения
        } else {
            gameOver = true;
        }
    }

    // Подтверждение продолжения игры
    public void confirmContinue() {
        awaitingConfirmation = false;
        round++;
        currentWordIndex = 0;  // Сбросить индексацию слов
        startTimer();  // Запустить новый таймер для следующего раунда
    }

    // Проверка завершенности игры
    public boolean isGameOver() {
        return gameOver;
    }

    // Проверка, ожидает ли игра подтверждения
    public boolean isAwaitingConfirmation() {
        return awaitingConfirmation;
    }

    // Получить текущие баллы
    public int getScore() {
        return score;
    }

    public int getRound() {
        return round;
    }

    public long getChatId() {
        return chatId;
    }
}
