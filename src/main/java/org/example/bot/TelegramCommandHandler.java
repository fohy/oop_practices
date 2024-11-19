package org.example.bot;

import org.example.bot.KeyboardHelper;
import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramCommandHandler {

    private final AliasGameService gameService;

    public TelegramCommandHandler(AliasGameService gameService) {
        this.gameService = gameService;
    }

    public SendMessage handleCommand(String command, Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId.toString());

        // Проверяем состояние игры для текущего chatId
        GameState gameState = gameService.getGameState(chatId);

        // Проверяем, начата ли игра
        if (gameState == null) {
            gameService.startNewGame(chatId);
        }

        switch (command.toLowerCase()) {
            case "/help":
                response.setText("Привет! Я бот для игры в Аллиас. Вот команды, которые ты можешь использовать:\n\n" +
                        "/start - Начать игру\n" +
                        "/help - Показать это сообщение\n" +
                        "/rules - Показать правила игры");
                break;
            case "/start":
                gameService.startNewGame(chatId);  // Начало новой игры
                response.setText("Привет! Давай играть в Аллиас! Выбери тему для игры:");
                response.setReplyMarkup(KeyboardHelper.createThemeSelectionKeyboard());
                break;
            case "/rules":
                response.setText("Правила игры в Аллиас:\n\n" +
                        "1. В игре участвуют два или больше игроков.\n" +
                        "2. Игроки поочередно загадывают слово, а другие пытаются его угадать.\n" +
                        "3. За каждое правильное отгаданное слово игроки получают очки.\n" +
                        "5. Можно пропустить слово, если оно не удается угадать.\n" +
                        "6. После окончания игры, можно начать новую, выбрав новую тему.");
                response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                break;
            case "начать игру":
                // Начинаем игру с выбранной темой
                String word = gameService.startGame(chatId);
                response.setText(word);
                response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                break;
            case "следующее":
                String nextWord = gameService.nextWord(chatId, true);
                response.setText(nextWord);
                response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                break;
            case "пропустить":
                String skippedWord = gameService.skipWord(chatId);
                response.setText(skippedWord);
                response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                break;
            case "готово":
                gameService.confirmContinue(chatId);
                String wordForNextRound = gameService.nextWord(chatId, true);
                response.setText(wordForNextRound);
                response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                break;
            case "технологии":
                gameService.selectTheme(chatId, "Технологии");
                response.setText("Вы выбрали тему 'Технологии'. Игра начнется с соответствующих слов!");
                response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                break;
            case "животные":
                gameService.selectTheme(chatId, "Животные");
                response.setText("Вы выбрали тему 'Животные'. Игра начнется с соответствующих слов!");
                response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                break;
            case "еда":
                gameService.selectTheme(chatId, "Еда");
                response.setText("Вы выбрали тему 'Еда'. Игра начнется с соответствующих слов!");
                response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                break;
            case "начать новую игру":
                // Сбросить игру и вернуться к выбору темы
                gameService.resetGame(chatId);
                response.setText("Новая игра началась! Выберите тему для игры.");
                response.setReplyMarkup(KeyboardHelper.createThemeSelectionKeyboard());
                break;
            default:
                response.setText("Неизвестная команда. Используйте /help для получения справки.");
                break;
        }

        // Если игра завершена, показываем клавиатуру для новой игры
        if (gameService.isGameOver(chatId)) {
            response.setReplyMarkup(KeyboardHelper.createNewGameKeyboard());
        }

        return response;
    }
}
