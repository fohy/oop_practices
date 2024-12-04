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
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));

        if (gameState == null) {
            gameService.startNewGame(Long.parseLong(chatId));
        }

        String responseMessage = "";
        ReplyKeyboardMarkup replyMarkup = null;

        switch (command.toLowerCase()) {
            case "/start":
                gameService.startNewGame(Long.parseLong(chatId));
                responseMessage = "Привет! Выберите команду и тему игры:\n";
                replyMarkup = KeyboardHelper.createTeamSelectionKeyboard();
                break;

            case "ежиная перхоть":
            case "лосиный сфинктер":
                // Если команда еще не выбрана
                if (gameState.getTeam1Name() == null) {
                    // Сначала выбираем команду
                    gameState.selectTeam(command, "Лосиный сфинктер".equals(command) ? "Ежиная перхоть" : "Лосиный сфинктер");
                    responseMessage = "Вы выбрали команду '" + command + "'. Теперь выберите тему игры!";
                    replyMarkup = KeyboardHelper.createThemeSelectionKeyboard();
                } else {
                    responseMessage = "Команды уже выбраны!";
                    replyMarkup = KeyboardHelper.createStartGameKeyboard();
                }
                break;

            case "технологии":
            case "животные":
            case "еда":
                // Если команда уже выбрана, выбираем тему
                if (gameState.getTeam1Name() != null) {
                    gameState.selectTheme(command);
                    responseMessage = "Вы выбрали тему '" + command + "'. Начнем игру!";
                    replyMarkup = KeyboardHelper.createStartGameKeyboard();
                } else {
                    responseMessage = "Сначала выберите команду!";
                    replyMarkup = KeyboardHelper.createTeamSelectionKeyboard();
                }
                break;

            case "начать игру":
                if (gameState.getTeam1Name() != null && gameState.getCurrentTheme() != null) {
                    gameState.startGame();
                    responseMessage = "Игра началась! Время пошло!";
                    replyMarkup = KeyboardHelper.createNextWordKeyboard();
                } else {
                    responseMessage = "Для начала игры необходимо выбрать команду и тему!";
                    replyMarkup = KeyboardHelper.createTeamAndThemeSelectionKeyboard();
                }
                break;

            case "следующее":
                String nextWord = gameState.nextWord(true);
                responseMessage = nextWord;
                break;

            case "пропустить":
                nextWord = gameState.skipWord();
                responseMessage = nextWord;
                break;

            default:
                responseMessage = "Неизвестная команда!";
                break;
        }

        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
