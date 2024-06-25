package org.example.utils;

public final class Constants {
    public static final String TITLE = "MozgoQuiz в Вроцлаве - интеллектуальный квиз с вопросами и ответами";
    public static final String MOZGO_QUIZ_GAME_TYPE = "MozgoQuiz";
    public static final String MOZGO_QUIZ_URL = "https://wro.mzgb.net/";
    public static final String SHEET_DATA_FILE = "Sheet.xlsx";
    public static final Integer MINIMUM_NUMBER_OF_PLAYERS = 2;

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
