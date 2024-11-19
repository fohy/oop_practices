package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {

    // Helper method to create a keyboard
    private static ReplyKeyboardMarkup createKeyboard(List<String> buttonLabels) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtonsRow = new KeyboardRow();  // Используем KeyboardRow вместо List<KeyboardButton>

        // Добавляем кнопки в текущий ряд
        for (String label : buttonLabels) {
            keyboardButtonsRow.add(new KeyboardButton(label));
        }

        // Создаем клавиатуру
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardButtonsRow);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    // Клавиатура для начала игры
    public static ReplyKeyboardMarkup createThemeSelectionKeyboard() {
        return createKeyboard(List.of("Технологии", "Животные", "Еда"));
    }

    // Клавиатура для игры
    public static ReplyKeyboardMarkup createGameKeyboard() {
        return createKeyboard(List.of("Следующее", "Пропустить", "Начать новую игру"));
    }

    // Клавиатура для новой игры
    public static ReplyKeyboardMarkup createNewGameKeyboard() {
        return createKeyboard(List.of("Начать новую игру"));
    }

    // Клавиатура для старта
    public static ReplyKeyboardMarkup createStartKeyboard() {
        return createKeyboard(List.of("Начать игру"));
    }
}
