package org.example.bot;

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

        GameState gameState = gameService.getGameState(chatId);
        if (gameState == null) {
            gameService.startNewGame(chatId);
        }

        switch (command.toLowerCase()) {

            case "/start":
                gameService.startNewGame(chatId);
                response.setText("Привет! Давай играть в Аллиас! Выбери тему для игры.");
                response.setReplyMarkup(KeyboardHelper.createThemeSelectionKeyboard());
                break;
            case "/help":
                response.setText("Бог поможет!");
                break;
            case "/rules":
                response.setText("Правила игры в Аллиас:\n\n1. Игра состоит из нескольких раундов.\n2. Игроки по очереди отгадывают слово.\n3. За каждое правильное отгаданное слово начисляются очки.");
                response.setReplyMarkup(KeyboardHelper.createStartKeyboard());
                break;

            case "начать игру":
                String word = gameService.startGame(chatId);
                response.setText(word);
                response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                break;

            case "следующее":
                String nextWord = gameService.nextWord(chatId, true);
                response.setText(nextWord);
                assert gameState != null;
                if (gameState.isGameOver()) {
                    response.setReplyMarkup(KeyboardHelper.createNewGameKeyboard());  // Кнопка для новой игры
                } else {
                    response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                }
                break;

            case "пропустить":
                String skippedWord = gameService.skipWord(chatId);
                response.setText(skippedWord);
                assert gameState != null;
                if (gameState.isGameOver()) {
                    response.setReplyMarkup(KeyboardHelper.createNewGameKeyboard());  // Кнопка для новой игры
                } else {
                    response.setReplyMarkup(KeyboardHelper.createGameKeyboard());
                }
                break;

            case "начать новую игру":
                gameService.endGame(chatId);
                gameService.startNewGame(chatId);
                response.setText("Новая игра началась! Выберите тему для игры.");
                response.setReplyMarkup(KeyboardHelper.createThemeSelectionKeyboard());
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

            default:
                response.setText("Неизвестная команда. Используйте /help для получения справки.");
                break;
        }

        return response;
    }
}