package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

    private static ReplyKeyboardMarkup createKeyboard(List<String> buttonLabels) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtonsRow = new KeyboardRow();

        for (String label : buttonLabels) {
            keyboardButtonsRow.add(new KeyboardButton(label));
        }

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardButtonsRow);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createThemeSelectionKeyboard() {
        return createKeyboard(List.of("Технологии", "Животные", "Еда"));
    }

    public static ReplyKeyboardMarkup createGameKeyboard() {
        return createKeyboard(List.of("Следующее", "Пропустить", "Начать новую игру"));
    }

    public static ReplyKeyboardMarkup createNewGameKeyboard() {
        return createKeyboard(List.of("Начать новую игру"));
    }

    public static ReplyKeyboardMarkup createStartKeyboard() {
        return createKeyboard(List.of("Начать игру"));
    }
}
