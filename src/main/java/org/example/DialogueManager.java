package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DialogueManager {
    private QuestionManager questionManager;

    public DialogueManager(QuestionManager questionsManager) {
        this.questionManager = questionsManager;
    }

    public boolean evaluateAnswer(String userAnswer, String correctAnswer) {
        // Оценка ответа
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }

    public SendMessage processMessage(Message msg) {
        String userMessage = msg.getText();
        SendMessage response = new SendMessage();
        response.setChatId(msg.getChatId().toString());

        // Если сообщение - это команда /start
        if (userMessage.equalsIgnoreCase("/start")) {
            response.setText("Привет, я бот! Я буду задавать тебе вопросы. Введи /help для справки.");
        } else if (userMessage.equalsIgnoreCase("/help")) {
            response.setText("Команды:\n/start - начать общение с ботом\n/help - получить справку");
        } else {
            // Работаем с вопросами
            Question question = questionManager.getNextQuestion();
            if (question != null) {
                response.setText(question.getQuestionText());
            } else {
                response.setText("Вы ответили на все вопросы! Введите /start, чтобы начать заново.");
                questionManager.reset();  // Сброс вопросов
            }
        }
        return response;
    }

    public SendMessage evaluateUserAnswer(Message msg) {
        String userAnswer = msg.getText();
        SendMessage response = new SendMessage();
        response.setChatId(msg.getChatId().toString());

        // Если пользователь ответил на вопрос, то оцениваем его
        Question question = questionManager.getNextQuestion();
        if (question != null && evaluateAnswer(userAnswer, question.getCorrectAnswer())) {
            response.setText("Правильный ответ!");
        } else {
            response.setText("Неправильный ответ! Правильный ответ: " + question.getCorrectAnswer());
        }

        return response;
    }
}
