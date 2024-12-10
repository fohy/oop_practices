package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.GameState;
import org.example.bot.IMessageSender;

public class SelectTeamCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;
    private final String teamName;

    public SelectTeamCommand(AliasGameService gameService, IMessageSender messageSender, String teamName) {
        this.gameService = gameService;
        this.messageSender = messageSender;
        this.teamName = teamName;
    }

    @Override
    public void execute(String chatId, String command) {
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));

        if (gameState.getTeam1Name() == null) {
            gameState.selectTeam(teamName, "Лосиный сфинктер".equals(teamName) ? "Ежиная перхоть" : "Лосиный сфинктер");
            messageSender.sendMessage(chatId, "Вы выбрали команду '" + teamName + "'. Теперь выберите тему игры!", KeyboardHelper.createThemeSelectionKeyboard());
        } else {
            messageSender.sendMessage(chatId, "Команды уже выбраны! Теперь выберите тему.", KeyboardHelper.createThemeSelectionKeyboard());
        }
    }
}
