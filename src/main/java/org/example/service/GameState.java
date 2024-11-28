    package org.example.service;

    import java.util.ArrayList;
    import java.util.List;

    public class GameState {

        private List<String> words;
        private int currentWordIndex;
        private int score;
        private boolean gameOver;
        private String currentTheme;


        public GameState() {
            this.words = new ArrayList<>();
            this.currentWordIndex = 0;
            this.score = 0;
            this.gameOver = false;
            this.currentTheme = "Технологии";
            initializeWords();
        }

        public void selectTheme(String theme) {
            this.currentTheme = theme;
            initializeWords();
        }

        public String getCurrentTheme() {
            return currentTheme;
        }

        private void initializeWords() {
            words.clear();
            switch (currentTheme) {
                case "Технологии":
                    words.add("Программирование");
                    words.add("Алгоритм");
                    words.add("Дебаг");
                    words.add("Синтаксис");
                    words.add("Компилятор");
                    break;
                case "Животные":
                    words.add("Кошка");
                    words.add("Птица");
                    words.add("Лев");
                    break;
                case "Еда":
                    words.add("Пицца");
                    words.add("Бургер");
                    words.add("Паста");
                    break;
            }
        }

        public String startGame() {
            this.currentWordIndex = 0;
            this.score = 0;
            this.gameOver = false;
            return words.get(currentWordIndex);
        }

        public String nextWord(boolean isCorrect) {
            if (gameOver) {
                return "Игра завершена! Ваши очки: " + score;
            }

            if (isCorrect) {
                score++;
            }

            currentWordIndex++;
            if (currentWordIndex >= words.size()) {
                gameOver = true;
                return "Игра завершена! Ваши очки: " + score;
            }

            return words.get(currentWordIndex);
        }

        public String skipWord() {
            if (gameOver) {
                return "Игра завершена! Ваши очки: " + score;
            }
            currentWordIndex++;
            if (currentWordIndex >= words.size()) {
                gameOver = true;
                return "Игра завершена! Ваши очки: " + score;
            }
            return words.get(currentWordIndex);
        }



        public int getScore() {
            return score;
        }

        public boolean isGameOver() {
            return gameOver;
        }

        public void resetGame() {
            this.currentWordIndex = 0;
            this.score = 0;
            this.gameOver = false;
            initializeWords();
        }
        public void endGame() {
            this.gameOver = true;
            this.currentWordIndex = words.size();
        }
    }
