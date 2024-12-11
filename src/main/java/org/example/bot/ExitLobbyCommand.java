package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.Lobby;
import org.example.bot.IMessageSender;

public class ExitLobbyCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public ExitLobbyCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        Long chatIdLong = Long.parseLong(chatId);

        // Ищем лобби, в котором находится игрок
        Lobby lobby = gameService.getLobbyByParticipant(chatIdLong);
        if (lobby == null) {
            messageSender.sendMessage(chatId, "Вы не находитесь в лобби, чтобы выйти.", null);
            return;
        }

        // Удаляем участника из лобби
        if (gameService.exitLobby(lobby.getLobbyId(), chatIdLong)) {
            messageSender.sendMessage(chatId, "Вы успешно покинули лобби!", KeyboardHelper.createLobbyMenuKeyboard());
        } else {
            messageSender.sendMessage(chatId, "Произошла ошибка при выходе из лобби. Попробуйте снова.", null);
        }
    }
}
