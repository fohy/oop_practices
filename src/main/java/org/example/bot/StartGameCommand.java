package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.example.bot.IMessageSender;
import org.example.bot.KeyboardHelper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class StartGameCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public StartGameCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));
        String responseMessage = "";
        ReplyKeyboardMarkup replyMarkup = null;

        if (gameState.getTeam1Name() != null && gameState.getCurrentTheme() != null) {
            gameState.startGame();
            responseMessage = "Игра началась! Время пошло!";
            replyMarkup = KeyboardHelper.createNextWordKeyboard();
        } else {
            responseMessage = "Для начала игры необходимо выбрать команду и тему!";
            replyMarkup = KeyboardHelper.createTeamAndThemeSelectionKeyboard();
        }

        messageSender.sendMessage(chatId, responseMessage, replyMarkup);
    }
}
