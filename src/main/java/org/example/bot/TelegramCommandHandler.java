package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.List;

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

        // Создаем клавиатуру с кнопками "Начать игру досрочно" и другими действиями
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
            int lobbyId = Integer.parseInt(lobbyCode.trim());  // Преобразуем код лобби в число
            Long chatIdLong = Long.parseLong(chatId);

            if (gameService.joinLobby(lobbyId, chatIdLong)) {
                messageSender.sendMessage(chatId,
                        "Вы успешно присоединились к лобби " + lobbyId + "!",
                        KeyboardHelper.createStartGameKeyboard());
            } else {
                messageSender.sendMessage(chatId,
                        "Лобби с кодом " + lobbyId + " не существует.",
                        null);
            }
        } catch (NumberFormatException e) {
            messageSender.sendMessage(chatId,
                    "Пожалуйста, введите корректный числовой код лобби.",
                    null);
        }
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

            case "создать лобби":  // Убедитесь, что тут правильный регистр и пробелы
                createLobby(chatId);
                break;

            case "войти в лобби по коду":
                askForLobbyCode(chatId);
                break;

            case "ввести код лобби":
                handleLobbyCode(chatId, command);
                break;

            case "начать игру досрочно":
                // Досрочное начало игры
                int lobbyId = gameService.getLobbyIdByChatId(Long.parseLong(chatId)); // Получаем lobbyId по chatId (если нужно)

                if (gameService.startGameEarly(lobbyId)) {
                    // Запрашиваем выбор команды
                    responseMessage = "Игра начнется досрочно! Теперь выберите команду.";
                    replyMarkup = KeyboardHelper.createTeamSelectionKeyboard(); // Создаем клавиатуру для выбора команды
                } else {
                    responseMessage = "Невозможно начать игру досрочно. Лобби слишком мало участников.";
                }
                break;

            case "ежиная перхоть":
            case "лосиный сфинктер":
                if (gameState.getTeam1Name() == null) {
                    gameState.selectTeam(command, "Лосиный сфинктер".equals(command) ? "Ежиная перхоть" : "Лосиный сфинктер");
                    responseMessage = "Вы выбрали команду '" + command + "'. Теперь выберите тему игры!";
                    replyMarkup = KeyboardHelper.createThemeSelectionKeyboard(); // Клавиатура для выбора темы
                } else {
                    responseMessage = "Команды уже выбраны!";
                    replyMarkup = KeyboardHelper.createStartGameKeyboard(); // Если команды выбраны, предлагаем начать игру
                }
                break;

            case "технологии":
            case "животные":
            case "еда":
                if (gameState.getTeam1Name() != null) {
                    gameState.selectTheme(command);
                    responseMessage = "Вы выбрали тему '" + command + "'. Теперь игра начнется!";
                    replyMarkup = KeyboardHelper.createStartGameKeyboard(); // Кнопка для начала игры
                } else {
                    responseMessage = "Сначала выберите команду!";
                    replyMarkup = KeyboardHelper.createTeamSelectionKeyboard(); // Если команда не выбрана, возвращаем к выбору команды
                }
                break;

            case "начать игру":
                if (gameState.getTeam1Name() != null && gameState.getCurrentTheme() != null) {
                    gameState.startGame();
                    responseMessage = "Игра началась! Время пошло!";
                    replyMarkup = KeyboardHelper.createNextWordKeyboard(); // Кнопка для следующего слова
                } else {
                    responseMessage = "Для начала игры необходимо выбрать команду и тему!";
                    replyMarkup = KeyboardHelper.createTeamAndThemeSelectionKeyboard(); // Напоминаем выбрать команду и тему
                }
                break;

            case "следующее":
                String nextWord = gameState.nextWord(true);
                responseMessage = nextWord;
                break;

            case "пропустить":
                String skipWord = gameState.skipWord();
                responseMessage = skipWord;
                break;

            default:
                responseMessage = "Неизвестная команда!";
                break;
        }

        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
