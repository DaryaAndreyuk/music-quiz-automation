import basecomponents.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.pages.RegisterGamePage;
import org.example.pages.UpcomingGamesPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTeamTest extends BaseTest {

    private UpcomingGamesPage upcomingGamesPage;
    private RegisterGamePage registerGamePage;

    private void loginAndNavigateToUpcomingGames(Map<String, String> dataMap) {
        landingPage.closeCookieAlert();
        upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
    }

    private void navigateToRegisterGamePage() {
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);
        registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        waitForModalElement();
    }

    private void fillPersonalDataAndProceed(Map<String, String> dataMap) {
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
    }

    private void fillTeamDataAndProceed(Map<String, String> dataMap) {
        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(dataMap.get(NUMBER_OF_TEAMMATES)));
        registerGamePage.clickFurtherButton();
        registerGamePage.waitForWebElementToAppear(registerGamePage.getFinalRegButton());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Register Team Validation")
    @Description("Verify team registration process")
    @Severity(SeverityLevel.CRITICAL)
    public void registerTeamValidation() {
        Map<String, String> dataMap = retrieveDataMap();
        loginAndNavigateToUpcomingGames(dataMap);
        navigateToRegisterGamePage();
        fillPersonalDataAndProceed(dataMap);
        fillTeamDataAndProceed(dataMap);
        assertTrue(registerGamePage.getFinalRegButton().isDisplayed() && registerGamePage.getFinalRegButton().isEnabled());
        // registerGamePage.registerTeam();
    }

    @Test
    @Tag("positive")
    @DisplayName("Verify login data in input fields matches Excel data")
    public void loginDataInExcelValidation() {
        Map<String, String> dataMap = retrieveDataMap();
        landingPage.closeCookieAlert();
        landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        assertTrue(dataMap.containsValue(landingPage.getEmail()));
        assertTrue(dataMap.containsValue(landingPage.getPassword()));
    }

    @Test
    @Tag("positive")
    @DisplayName("Verify name, phone, email data in input fields matches Excel data")
    public void personalDataInExcelValidation() {
        Map<String, String> dataMap = retrieveDataMap();

        landingPage.closeCookieAlert();
        upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);
        registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        waitForModalElement();
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        assertTrue(dataMap.containsValue(registerGamePage.getFio()));
        assertTrue(dataMap.containsValue(registerGamePage.getPhone()));
        assertTrue(dataMap.containsValue(registerGamePage.getEmail()));
        registerGamePage.clickFurtherButton();
    }

    @Test
    @Tag("positive")
    @DisplayName("Verify team name and number of teammates in input fields matches Excel data")
    public void teamDataInExcelValidation() {
        Map<String, String> dataMap = retrieveDataMap();

        landingPage.closeCookieAlert();
        upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);
        registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        waitForModalElement();
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(String.valueOf(dataMap.get(NUMBER_OF_TEAMMATES))));
        assertTrue(dataMap.containsValue(registerGamePage.getTeamName()));
        assertTrue(dataMap.containsValue(registerGamePage.getNumberOfPlayers()));
        registerGamePage.clickFurtherButton();
    }

    private void waitForModalElement() {
        WebElement modalElement = registerGamePage.waitForElementToAppear(By.className("modal"));
        registerGamePage.waitForWebElementToBeClickable(modalElement);
    }
}
