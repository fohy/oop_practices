package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class TelegramCommandHandler {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public TelegramCommandHandler(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    public void handleCommand(String command, String chatId) {
        // Получаем состояние игры для текущего чата
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));

        // Если игры нет, начинаем новую
        if (gameState == null) {
            gameService.startNewGame(Long.parseLong(chatId));
        }

        // Инициализация сообщения и клавиатуры
        String responseMessage = "";
        ReplyKeyboardMarkup replyMarkup = null;  // Используем объект ReplyKeyboardMarkup

        // Обработка команд
        switch (command.toLowerCase()) {
            case "/start":
                // Запуск новой игры и выбор темы
                gameService.startNewGame(Long.parseLong(chatId));
                responseMessage = "Привет! Давай играть в Аллиас! Выбери тему для игры.";
                replyMarkup = KeyboardHelper.createThemeSelectionKeyboard(); // Клавиатура для выбора темы
                break;

            case "/help":
                responseMessage = "Бог поможет! Используй команды:\n" +
                        "/start - начать новую игру\n" +
                        "/rules - показать правила игры\n" +
                        "технологии, животные, еда - выбрать тему для игры\n" +
                        "начать игру - начать игру с выбранной темой\n" +
                        "следующее - переход к следующему слову\n" +
                        "пропустить - пропустить слово";
                break;

            case "/rules":
                responseMessage = "Правила игры в Аллиас:\n\n" +
                        "1. Игра состоит из нескольких раундов.\n" +
                        "2. Игроки по очереди отгадывают слово.\n" +
                        "3. За каждое правильное отгаданное слово начисляются очки.";
                replyMarkup = KeyboardHelper.createStartKeyboard(); // Клавиатура для старта игры
                break;

            case "начать игру":
                // Начинаем игру с выбранной темой
                String word = gameService.startGame(Long.parseLong(chatId));
                responseMessage = "Начинаем игру! \n" + word;
                replyMarkup = KeyboardHelper.createGameKeyboard(); // Клавиатура для игрового процесса
                break;

            case "следующее":
                // Переход к следующему слову
                String nextWord = gameService.nextWord(Long.parseLong(chatId), true);  // true, если слово угадано
                responseMessage = nextWord;

                if (gameState.isGameOver()) {
                    responseMessage = "Игра завершена! Ваши очки: " + gameState.getScore();
                    replyMarkup = KeyboardHelper.createNewGameKeyboard(); // Кнопка для новой игры
                } else {
                    replyMarkup = KeyboardHelper.createGameKeyboard();  // Продолжаем игру
                }
                break;

            case "пропустить":
                String skippedWord = gameService.skipWord(Long.parseLong(chatId));
                responseMessage = skippedWord;

                if (gameState.isGameOver()) {
                    responseMessage = "Игра завершена! Ваши очки: " + gameState.getScore();
                    replyMarkup = KeyboardHelper.createNewGameKeyboard();  // Кнопка для новой игры
                } else {
                    replyMarkup = KeyboardHelper.createGameKeyboard();  // Продолжаем игру
                }
                break;

            case "начать новую игру":
                gameService.endGame(Long.parseLong(chatId));
                gameService.startNewGame(Long.parseLong(chatId));
                responseMessage = "Новая игра началась! Выберите тему для игры.";
                replyMarkup = KeyboardHelper.createThemeSelectionKeyboard(); // Клавиатура для выбора темы
                break;

            case "технологии":
                gameService.selectTheme(Long.parseLong(chatId), "Технологии");
                responseMessage = "Вы выбрали тему 'Технологии'. Игра начнется с соответствующих слов!";
                replyMarkup = KeyboardHelper.createStartKeyboard();  // Клавиатура для старта игры
                break;

            case "животные":
                gameService.selectTheme(Long.parseLong(chatId), "Животные");
                responseMessage = "Вы выбрали тему 'Животные'. Игра начнется с соответствующих слов!";
                replyMarkup = KeyboardHelper.createStartKeyboard();  // Клавиатура для старта игры
                break;

            case "еда":
                gameService.selectTheme(Long.parseLong(chatId), "Еда");
                responseMessage = "Вы выбрали тему 'Еда'. Игра начнется с соответствующих слов!";
                replyMarkup = KeyboardHelper.createStartKeyboard();  // Клавиатура для старта игры
                break;

            default:
                responseMessage = "Неизвестная команда! Используйте /start для начала.";
        }

        // Отправляем сообщение с клавиатурой
        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
