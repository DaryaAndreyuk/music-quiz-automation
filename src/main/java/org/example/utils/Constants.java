package org.example.utils;

public final class Constants {
    public static final String MOZGO_QUIZ_GAME_TYPE = "MozgoQuiz";
    public static final String MOZGO_QUIZ_URL = "https://wro.mzgb.net/";
    public static final String SHEET_DATA_FILE = "Sheet.xlsx";
    public static final String NO_SHEET_DATA_FILE = "NoSheet.xlsx";
    public static final String EMPTY_SHEET_DATA_FILE = "EmptySheet.xlsx";
    public static final String MISSING_REQUIRED_SHEET_DATA_FILE =  "MissingRequiredSheet.xlsx";
    public static final Integer MINIMUM_NUMBER_OF_PLAYERS = 2;
    public static final String CREATE_NEW_OPTION = "Создать новую";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String TEAM = "Team";
    public static final String NUMBER_OF_TEAMMATES = "Number of teammates";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
