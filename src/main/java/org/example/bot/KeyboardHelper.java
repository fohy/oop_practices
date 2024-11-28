package org.example.bot;

public class KeyboardHelper {

    // Пример корректной клавиатуры с кнопками
    public static String createThemeSelectionKeyboardJson() {
        return "{\"keyboard\": [[\"Технологии\", \"Животные\", \"Еда\"]], \"resize_keyboard\": true, \"one_time_keyboard\": true}";
    }

    // Пример клавиатуры для новой игры
    public static String createGameKeyboardJson() {
        return "{\"keyboard\": [[\"Следующее\", \"Пропустить\", \"Начать новую игру\"]], \"resize_keyboard\": true}";
    }

    // Пример клавиатуры с одной кнопкой для начала новой игры
    public static String createNewGameKeyboardJson() {
        return "{\"keyboard\": [[\"Начать новую игру\"]], \"resize_keyboard\": true}";
    }

    // Пример клавиатуры для старта
    public static String createStartKeyboardJson() {
        return "{\"keyboard\": [[\"Начать игру\"]], \"resize_keyboard\": true}";
    }
}
