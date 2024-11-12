package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionManager {
    private List<Question> questions;
    private int index;

    public QuestionManager() {
        this.questions = new ArrayList<>();
        this.index = 0;

        // Добавляем вопросы
        this.questions.add(new Question("Сколько будет 2 + 2?", "4"));
        this.questions.add(new Question("Какого цвета небо?", "синий"));
        this.questions.add(new Question("Сколько дней в неделе?", "7"));
        this.questions.add(new Question("Какая столица Франции?", "Париж"));
        this.questions.add(new Question("Кто написал 'Войну и мир'?", "Толстой"));
        this.questions.add(new Question("Что такое ООП?", "объектно-ориентированное программирование"));
        this.questions.add(new Question("Сколько букв в алфавите?", "33"));
        this.questions.add(new Question("Как называется химический элемент с символом O?", "Кислород"));
        this.questions.add(new Question("Какой самый большой океан?", "Тихий"));
        this.questions.add(new Question("Какая планета находится ближе всех к Солнцу?", "Меркурий"));
        this.questions.add(new Question("ФИИТ?","сосать"));
        // Перемешиваем вопросы случайным образом
        Collections.shuffle(questions);
    }

    // Возвращает следующий вопрос
    public Question getNextQuestion() {
        if (index < questions.size()) {
            return questions.get(index++);
        } else {
            return null; // Все вопросы заданы
        }
    }

    // Возвращает текущий вопрос
    public Question getCurrentQuestion() {
        if (index > 0 && index <= questions.size()) {
            return questions.get(index - 1);
        }
        return null; // Возвращаем текущий вопрос
    }

    // Получаем общее количество вопросов
    public int getTotalQuestions() {
        return questions.size();
    }

    // Сброс индекса для нового раунда
    public void reset() {
        this.index = 0;
        Collections.shuffle(questions);  // Перемешиваем вопросы для нового раунда
    }
}
