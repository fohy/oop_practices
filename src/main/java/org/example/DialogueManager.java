package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class DialogueManager {
    private GameSession gameSession;
    private KeyboardManager keyboardManager;

    public DialogueManager(GameSession gameSession, KeyboardManager keyboardManager) {
        this.gameSession = gameSession;
        this.keyboardManager = keyboardManager;
    }

    // Метод для обработки сообщений
    public SendMessage processMessage(Message msg) {
        String userMessage = msg.getText();
        SendMessage response = new SendMessage();
        response.setChatId(msg.getChatId().toString());

        switch (userMessage.toLowerCase()) {
            case "/stop":
                // Отправляем утешительное сообщение пользователю
                response.setText("Чё сдался да, ну ниче, яйца отрастут - попробуешь ещё раз!");

                // Сбрасываем игровую сессию
                gameSession.reset();
                gameSession.endGame();
                // Показываем клавиатуру с кнопкой "Начать игру"
                response.setReplyMarkup(keyboardManager.createStartGameKeyboard());
                break;

            case "/descript":
                response.setText("Бот будет отправлять тебе вопросы, твоя задача — правильно на них ответить.");
                break;

            case "/start":
                // Команда /start — показываем кнопку для начала игры, если она ещё не началась
                if (!gameSession.isGameStarted()) {
                    response.setText("Привет! Я бот, давай поиграем в игру. Когда будешь готов, нажми 'Начать игру'.");

                    // Показать клавиатуру с кнопкой "Начать игру"
                    response.setReplyMarkup(keyboardManager.createStartGameKeyboard());
                }
                break;

            case "/help":
                response.setText("Команды:\n/start - начать игру\n/help - получить справку");
                break;

            default:
                if (userMessage.equals("Начать игру") && !gameSession.isGameStarted()) {
                    // Начинаем игру
                    gameSession.startGame();
                    response.setText("Отлично! Начинаем игру!");

                    // Убираем клавиатуру после нажатия на "Начать игру"
                    ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
                    keyboardRemove.setRemoveKeyboard(true);
                    response.setReplyMarkup(keyboardRemove);

                    // Получаем первый вопрос
                    Question question = gameSession.getNextQuestion();
                    if (question != null) {
                        response.setText(question.getQuestionText());
                    } else {
                        response.setText("Все вопросы заданы. Введите /start для нового раунда.");
                        gameSession.reset();  // Если все вопросы заданы, сбрасываем и начинаем заново
                    }
                } else {
                    // Обработка ответа на вопрос
                    Question currentQuestion = gameSession.getCurrentQuestion();
                    if (currentQuestion != null) {
                        if (evaluateAnswer(userMessage, currentQuestion.getCorrectAnswer())) {
                            gameSession.incrementScore();
                            response.setText("Правильный ответ!");
                        } else {
                            response.setText("Неправильный ответ! Правильный ответ: " + currentQuestion.getCorrectAnswer());
                        }

                        // После ответа на текущий вопрос - следующий
                        currentQuestion = gameSession.getNextQuestion();
                        if (currentQuestion != null) {
                            response.setText(response.getText() + "\nСледующий вопрос: " + currentQuestion.getQuestionText());
                        } else {
                            response.setText(response.getText() + "\nВы ответили на все вопросы! Ваш результат: " + gameSession.getScore() + " из " + gameSession.getTotalQuestions());
                            gameSession.reset();  // Если все вопросы заданы, сбрасываем
                        }
                    }
                }
                break;
        }

        return response;
    }

    // Метод для оценки ответа пользователя
    private boolean evaluateAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
}
