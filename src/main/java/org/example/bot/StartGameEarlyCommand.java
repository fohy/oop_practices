package org.example.bot;

import org.example.service.AliasGameService;
import org.example.bot.IMessageSender;
import org.example.bot.KeyboardHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class StartGameEarlyCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public StartGameEarlyCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        int lobbyId = gameService.getLobbyIdByChatId(Long.parseLong(chatId));
        String responseMessage;
        ReplyKeyboardMarkup replyMarkup = null;

        if (gameService.startGameEarly(lobbyId)) {
            responseMessage = "Игра начнется досрочно! Теперь выберите команду.";
            replyMarkup = KeyboardHelper.createTeamSelectionKeyboard();
        } else {
            responseMessage = "Невозможно начать игру досрочно. Лобби слишком мало участников.";
        }

        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
