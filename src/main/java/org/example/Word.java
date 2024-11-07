package org.example;

public class Word {
    private final String word;
    private final String correctDescription;

    public Word(String word, String correctDescription) {
        this.word = word;
        this.correctDescription = correctDescription;
    }

    public String getWord() {
        return word;
    }

    public String getCorrectDescription() {
        return correctDescription;
    }
}
