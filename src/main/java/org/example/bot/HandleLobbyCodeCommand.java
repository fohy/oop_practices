package org.example.bot;

import org.example.service.AliasGameService;
import org.example.service.Lobby;
import org.example.bot.IMessageSender;

public class HandleLobbyCodeCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public HandleLobbyCodeCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        try {
            int lobbyId = Integer.parseInt(command.trim());
            Long chatIdLong = Long.parseLong(chatId);

            Lobby lobby = gameService.getLobbyById(lobbyId);
            if (lobby == null) {
                messageSender.sendMessage(chatId, "Лобби с кодом " + lobbyId + " не существует. Пожалуйста, попробуйте снова.", null);
                return;
            }

            if (lobby.isFull()) {
                messageSender.sendMessage(chatId, "Лобби с кодом " + lobbyId + " уже заполнено. Попробуйте войти в другое лобби.", null);
                return;
            }

            if (gameService.joinLobby(lobbyId, chatIdLong)) {
                messageSender.sendMessage(chatId, "Вы успешно присоединились к лобби " + lobbyId + "!", KeyboardHelper.createStartGameKeyboard());
            } else {
                messageSender.sendMessage(chatId, "Не удалось присоединиться к лобби " + lobbyId + ". Попробуйте снова.", null);
            }

        } catch (NumberFormatException e) {
            messageSender.sendMessage(chatId, "Пожалуйста, введите корректный числовой код лобби.", null);
        }
    }
}
