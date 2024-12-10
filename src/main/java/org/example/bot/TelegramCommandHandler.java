package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.example.service.Lobby;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class TelegramCommandHandler {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public TelegramCommandHandler(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    private void createLobby(String chatId) {
        Long chatIdLong = Long.parseLong(chatId);
        int lobbyId = gameService.createLobby(chatIdLong);

        ReplyKeyboardMarkup keyboardMarkup = KeyboardHelper.createLobbyWithEarlyStartKeyboard(lobbyId);

        messageSender.sendMessage(chatId,
                "Лобби создано! Ваш код: " + lobbyId + "\nОжидаем второго игрока...",
                keyboardMarkup);
    }

    private void askForLobbyCode(String chatId) {
        messageSender.sendMessage(chatId,
                "Введите код лобби для подключения:",
                null);
    }

    private void handleLobbyCode(String chatId, String lobbyCode) {
        try {
            int lobbyId = Integer.parseInt(lobbyCode.trim());
            Long chatIdLong = Long.parseLong(chatId);

            Lobby lobby = gameService.getLobbyById(lobbyId);
            if (lobby == null) {
                messageSender.sendMessage(chatId,
                        "Лобби с кодом " + lobbyId + " не существует. Пожалуйста, попробуйте снова.",
                        null);
                return;
            }

            if (lobby.isFull()) {
                messageSender.sendMessage(chatId,
                        "Лобби с кодом " + lobbyId + " уже заполнено. Попробуйте войти в другое лобби.",
                        null);
                return;
            }

            if (gameService.joinLobby(lobbyId, chatIdLong)) {
                messageSender.sendMessage(chatId,
                        "Вы успешно присоединились к лобби " + lobbyId + "!",
                        KeyboardHelper.createStartGameKeyboard());
            } else {
                messageSender.sendMessage(chatId,
                        "Не удалось присоединиться к лобби " + lobbyId + ". Попробуйте снова.",
                        null);
            }

        } catch (NumberFormatException e) {
            messageSender.sendMessage(chatId,
                    "Пожалуйста, введите корректный числовой код лобби.",
                    null);
        }
    }

    private void startRoundTimer(String chatId, GameState gameState) {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeLimit = 30000;  // Время на каждый ход

            while (System.currentTimeMillis() - startTime < timeLimit) {
                long remainingTime = timeLimit - (System.currentTimeMillis() - startTime);
                String timeMessage = "Осталось " + remainingTime / 1000 + " секунд.";
                messageSender.sendMessage(chatId, timeMessage, null);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            gameState.nextRound();
            messageSender.sendMessage(chatId, "Раунд завершен! Переходите к следующей команде.", null);

            if ("Лосиный сфинктер".equals(gameState.getCurrentTeam())) {
                messageSender.sendMessage(chatId, "Теперь ваша очередь выбрать тему.", KeyboardHelper.createThemeSelectionKeyboard());
            } else {
                messageSender.sendMessage(chatId, "Теперь ваша очередь! Выберите тему.", KeyboardHelper.createThemeSelectionKeyboard());
            }

            gameState.switchTeam();
        }).start();
    }


    public void handleCommand(String command, String chatId) {
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));
        Long chatIdLong = Long.valueOf(chatId);

        if (gameState == null) {
            gameService.startNewGame(Long.parseLong(chatId));
        }

        String responseMessage = "";
        ReplyKeyboardMarkup replyMarkup = null;

        switch (command.toLowerCase()) {
            case "/start":
                messageSender.sendMessage(chatId,
                        "Добро пожаловать в Алиас! Выберите действие:",
                        KeyboardHelper.createLobbyMenuKeyboard());
                break;

            case "создать лобби":
                createLobby(chatId);
                break;

            case "войти в лобби по коду":
                askForLobbyCode(chatId);
                break;

            case "ввести код лобби":
                handleLobbyCode(chatId, command);
                break;

            case "начать игру досрочно":
                int lobbyId = gameService.getLobbyIdByChatId(Long.parseLong(chatId));

                if (gameService.startGameEarly(lobbyId)) {
                    responseMessage = "Игра начнется досрочно! Теперь выберите команду.";
                    replyMarkup = KeyboardHelper.createTeamSelectionKeyboard();
                } else {
                    responseMessage = "Невозможно начать игру досрочно. Лобби слишком мало участников.";
                }
                break;

            case "лосиный сфинктер":
            case "ежиная перхоть":
                if (gameState.getTeam1Name() == null) {
                    gameState.selectTeam(command, "Лосиный сфинктер".equals(command) ? "Ежиная перхоть" : "Лосиный сфинктер");
                    responseMessage = "Вы выбрали команду '" + command + "'. Теперь выберите тему игры!";
                    replyMarkup = KeyboardHelper.createThemeSelectionKeyboard();
                } else {
                    responseMessage = "Команды уже выбраны! Теперь выберите тему.";
                    replyMarkup = KeyboardHelper.createTeamSelectionKeyboard();
                }
                break;

            case "технологии":
            case "животные":
            case "еда":
                if (gameState.getTeam1Name() != null && gameState.getCurrentTheme() == null) {
                    gameState.selectTheme(command);
                    responseMessage = "Вы выбрали тему '" + command + "'. Теперь игра начнется!";
                    replyMarkup = KeyboardHelper.createStartGameKeyboard();
                } else if (gameState.getTeam2Name() != null && gameState.getCurrentTheme() != null) {
                    gameState.selectTheme(command);
                    responseMessage = "Вы выбрали тему '" + command + "'. Игра начнется!";
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
                    startRoundTimer(chatId, gameState);  // Старт таймера для первой команды
                } else {
                    responseMessage = "Для начала игры необходимо выбрать команду и тему!";
                    replyMarkup = KeyboardHelper.createTeamAndThemeSelectionKeyboard();
                }
                break;
            case "выйти из лобби" :
                messageSender.sendMessage(chatId, "Возвращаемся в меню!", KeyboardHelper.createLobbyMenuKeyboard());
                break;

            case "следующее":
                if ("Team 1".equals(gameState.getCurrentTeam())) {
                    String nextWord = gameState.nextWord(true);
                    responseMessage = nextWord;
                } else {
                    responseMessage = "Это не ваша очередь!";
                }
                break;

            case "пропустить":
                if ("Team 1".equals(gameState.getCurrentTeam())) {
                    String skipWord = gameState.skipWord();
                    responseMessage = skipWord;
                } else {
                    responseMessage = "Это не ваша очередь!";
                }
                break;


            default:
                if (command.matches("\\d+")) {
                    handleLobbyCode(chatId, command);
                } else {
                    responseMessage = "Неизвестная команда!";
                }
                break;
        }

        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
