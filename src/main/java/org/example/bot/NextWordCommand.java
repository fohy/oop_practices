package org.example.bot;

import org.example.service.AliasGameService;
import org.example.bot.IMessageSender;
import org.example.service.GameState;

public class NextWordCommand implements Command {
    private final AliasGameService gameService;
    private final IMessageSender messageSender;

    public NextWordCommand(AliasGameService gameService, IMessageSender messageSender) {
        this.gameService = gameService;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(String chatId, String command) {
        GameState gameState = gameService.getGameState(Long.parseLong(chatId));

        if (gameState == null) {
            messageSender.sendMessage(chatId, "Игра не началась.", null);
            return;
        }

        // Проверяем, чья очередь
        if (gameState.getCurrentTeam().equals("Team 1")) {
            // Отправляем следующее слово
            String nextWord = gameState.nextWord(false);  // false, чтобы не увеличивать раунд при каждом слове
            if (nextWord != null && !nextWord.isEmpty()) {
                messageSender.sendMessage(chatId, nextWord, null);
            } else {
                messageSender.sendMessage(chatId, "Слова закончились!", null); // Если слов больше нет
            }

            // Выводим оставшееся время
            String timeRemaining = gameState.trackTime();
            messageSender.sendMessage(chatId, timeRemaining, null);
        } else {
            messageSender.sendMessage(chatId, "Это не ваша очередь!", null); // Сообщение, если не ваша очередь
        }
    }
}
