import basecomponents.BaseTest;
import io.qameta.allure.Description;
import org.example.pages.RegisterGamePage;
import org.example.pages.UpcomingGamesPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;

import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErrorValidationTest extends BaseTest {

    private UpcomingGamesPage upcomingGamesPage;

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
        validateErrorMessage("Заполните, пожалуйста, все необходимые поля.", "", "375294999632", "dsfd@fgfds.com");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty phone")
    @Description("This test verifies error message when attempting to enter empty phone number")
    public void emptyPhoneErrorValidation() {
        validateErrorMessage("Заполните, пожалуйста, все необходимые поля.", "TestName", "", "dsfd@fgfds.com");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty email")
    @Description("This test verifies error message when attempting to enter empty email")
    public void emptyEmailErrorValidation() {
        validateErrorMessage("Заполните, пожалуйста, все необходимые поля.", "TestName", "375294999632", "");
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid email format")
    @ValueSource(strings = {
            "plainaddress",
            "missingatsymbol.com"
    })
    void emailFormatErrorValidation(String email) {
        validateErrorMessage("Email неверного формата.", "TestName", "375294999632", email);
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid phone format")
    @ValueSource(strings = {"1", "1234", "12345678"})
    void phoneFormatErrorValidation(String phoneNumber) {
        validateErrorMessage("Укажите полноценный номер телефона.", "TestName", phoneNumber, "dsfd@fgfds.com");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty team name")
    public void emptyTeamNameErrorValidation() {
        Map<String, String> dataMap = getDataMap();
        RegisterGamePage registerGamePage = getDataAndPerformLogin();
        fillPersonalDataAndProceed(registerGamePage, dataMap);
        registerGamePage.fillTeamData("", 5);
        clickAndValidateErrorMessage("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    private void fillPersonalDataAndProceed(RegisterGamePage registerGamePage, Map<String, String> dataMap) {
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
    }

    private void validateErrorMessage(String expectedErrorMessage, String name, String phone, String email) {
        RegisterGamePage registerGamePage = getDataAndPerformLogin();
        registerGamePage.fillPersonalData(name, phone, email);
        clickAndValidateErrorMessage(expectedErrorMessage, registerGamePage);
    }

    private void clickAndValidateErrorMessage(String expectedErrorMessage, RegisterGamePage registerGamePage) {
        registerGamePage.clickFurtherButton();
        assertEquals(expectedErrorMessage, registerGamePage.getErrorMessage());
    }

    private void performLogin(Map<String, String> dataMap) {
        landingPage.closeCookieAlert();
        upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
    }

    private RegisterGamePage getDataAndPerformLogin() {
        Map<String, String> dataMap = getDataMap();
        performLogin(dataMap);
        return getRegisterGamePage();
    }

    private RegisterGamePage getRegisterGamePage() {
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);
        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        WebElement modalElement = upcomingGamesPage.waitForElementToAppear(By.className("modal"));
        upcomingGamesPage.waitForWebElementToBeClickable(modalElement);
        return registerGamePage;
    }
}