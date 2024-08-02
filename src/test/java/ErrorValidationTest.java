import basecomponents.BaseTest;
import io.qameta.allure.Description;
import org.example.pages.LandingPage;
import org.example.pages.RegisterGamePage;
import org.example.pages.UpcomingGamesPage;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorValidationTest extends BaseTest {

    private LandingPage landingPage;
    private UpcomingGamesPage upcomingGamesPage;
    private WebDriverWait wait;

    private Map<String, String> getDataMap() {
        return ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
    }

    private void initialize() {
        landingPage = new LandingPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void performLogin(Map<String, String> dataMap) {
        landingPage.closeCookieAlert();
        upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
    }

    private RegisterGamePage getRegisterGamePage(Map<String, String> dataMap) {
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);

        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        wait.until(ExpectedConditions.elementToBeClickable(modalElement));
        return registerGamePage;
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for invalid login credentials")
    @Description("This test attempts to log into the website using a login and a password")
    public void loginErrorValidation() {
        performLoginAndCheckError("wrong.email@gmail.com", "Stronbbbssword1!", "Учётные данные не найдены в базе.");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty username")
    @Description("This test verifies error message when attempting to enter empty username")
    public void emptyUserNameErrorValidation() {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData("", "375294999632", "dsfd@fgfds.com");

        validateError("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty phone")
    @Description("This test verifies error message when attempting to enter empty phone number")
    public void emptyPhoneErrorValidation() {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData("TestName", "", "dsfd@fgfds.com");

        validateError("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty email")
    @Description("This test verifies error message when attempting to enter empty email")
    public void emptyEmailErrorValidation() {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData("TestName", "375294999632", "");

        validateError("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid email format")
    @ValueSource(strings = {
            "plainaddress",
            "missingatsymbol.com"
    })
    void emailFormatErrorValidation(String email) {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData("TestName", "375294999632", email);

        validateError("Email неверного формата.", registerGamePage);
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid phone format")
    @ValueSource(strings = {"1", "1234", "12345678"})
    void phoneFormatErrorValidation(String phoneNumber) {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData("TestName", phoneNumber, "dsfd@fgfds.com");

        validateError("Укажите полноценный номер телефона.", registerGamePage);
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty team name")
    public void emptyTeamNameErrorValidation() {
        initialize();
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);

        RegisterGamePage registerGamePage = getRegisterGamePage(dataMap);
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));

        registerGamePage.clickFurtherButton();
        registerGamePage.fillTeamData("", 5);

        validateError("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    private void validateError(String expectedErrorMessage, RegisterGamePage registerGamePage) {
        registerGamePage.clickFurtherButton();
        assertEquals(expectedErrorMessage, registerGamePage.getErrorMessage());
    }
}