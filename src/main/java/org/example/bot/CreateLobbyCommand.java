package org.example.bot;

import org.example.service.AliasGameService;

import org.example.bot.IMessageSender;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class CreateLobbyCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public CreateLobbyCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        Long chatIdLong = Long.parseLong(chatId);
        int lobbyId = gameService.createLobby(chatIdLong);

        ReplyKeyboardMarkup keyboardMarkup = KeyboardHelper.createLobbyWithEarlyStartKeyboard(lobbyId);

        messageSender.sendMessage(chatId,
                "Лобби создано! Ваш код: " + lobbyId + "\nОжидаем второго игрока...",
                keyboardMarkup);
    }
}
