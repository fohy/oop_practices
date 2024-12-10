package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.example.bot.IMessageSender;

public class SelectThemeCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;
    private final String theme;

    public SelectThemeCommand(AliasGameService gameService, IMessageSender messageSender, String theme) {
        this.gameService = gameService;
        this.messageSender = messageSender;
        this.theme = theme;
    }

    @Override
    public void execute(String chatId, String command) {
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));

        if (gameState.getTeam1Name() != null && gameState.getCurrentTheme() == null) {
            gameState.selectTheme(theme);
            messageSender.sendMessage(chatId, "Вы выбрали тему '" + theme + "'. Теперь игра начнется!", KeyboardHelper.createStartGameKeyboard());
        } else if (gameState.getTeam2Name() != null && gameState.getCurrentTheme() != null) {
            gameState.selectTheme(theme);
            messageSender.sendMessage(chatId, "Вы выбрали тему '" + theme + "'. Игра начнется!", KeyboardHelper.createStartGameKeyboard());
        } else {
            messageSender.sendMessage(chatId, "Сначала выберите команду!", KeyboardHelper.createTeamSelectionKeyboard());
        }
    }
}
