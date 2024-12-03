package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {
    public static ReplyKeyboardMarkup createTeamAndThemeSelectionKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();

        // Клавиатура для выбора команды
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Ежиная перхоть");
        row1.add("Лосиный сфинктер");
        rows.add(row1);

        // Клавиатура для выбора темы
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Технологии");
        row2.add("Животные");
        row2.add("Еда");
        rows.add(row2);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true);  // Появляется только один раз
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createTeamSelectionKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Ежиная перхоть");
        row1.add("Лосиный сфинктер");
        rows.add(row1);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createThemeSelectionKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Технологии");
        row1.add("Животные");
        row1.add("Еда");
        rows.add(row1);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createStartGameKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Начать игру");
        rows.add(row1);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createNextWordKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Следующее");
        row1.add("Пропустить");
        rows.add(row1);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createLobbyMenuKeyboard() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Создать лобби");
        row1.add("Войти в лобби по коду");
        rows.add(row1);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true); // Клавиатура исчезает после выбора
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup createLobbyWithEarlyStartKeyboard(int lobbyId) {
        List<KeyboardRow> rows = new ArrayList<>();

        // Кнопка "Начать игру досрочно"
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Начать игру досрочно");
        rows.add(row1);

        // Можно добавить другие кнопки, например, для выхода из лобби
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Выйти из лобби");
        rows.add(row2);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(true);  // Клавиатура появляется один раз

        return keyboardMarkup;
    }
}
