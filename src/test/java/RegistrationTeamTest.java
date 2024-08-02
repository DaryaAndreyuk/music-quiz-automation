import basecomponents.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.pages.LandingPage;
import org.example.pages.RegisterGamePage;
import org.example.pages.UpcomingGamesPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.utils.ExcelUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationTeamTest extends BaseTest {

    private Map<String, String> getDataMap() {
        return ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
    }

    private UpcomingGamesPage loginAndNavigateToUpcomingGames(Map<String, String> dataMap) {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
        return upcomingGamesPage;
    }

    private RegisterGamePage navigateToRegisterGamePage(UpcomingGamesPage upcomingGamesPage) {
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        wait.until(ExpectedConditions.elementToBeClickable(modalElement));
        return registerGamePage;
    }

    private void fillPersonalDataAndProceed(RegisterGamePage registerGamePage, Map<String, String> dataMap) {
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
    }


    @Test
    @Tag("smoke")
    @DisplayName("Register Team Validation")
    @Description("Verify team registration process")
    @Severity(SeverityLevel.CRITICAL)
    public void registerTeamValidation() {
        Map<String, String> dataMap = getDataMap();
        UpcomingGamesPage upcomingGamesPage = loginAndNavigateToUpcomingGames(dataMap);
        RegisterGamePage registerGamePage = navigateToRegisterGamePage(upcomingGamesPage);
        fillPersonalDataAndProceed(registerGamePage, dataMap);
        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(dataMap.get(NUMBER_OF_TEAMMATES)));
        registerGamePage.clickFurtherButton();
        registerGamePage.waitForWebElementToAppear(registerGamePage.getFinalRegButton());
        assertTrue(registerGamePage.getFinalRegButton().isDisplayed() && registerGamePage.getFinalRegButton().isEnabled());
        // registerGamePage.registerTeam();
    }

    @Test
    @Tag("positive")
    @DisplayName("Verify login data in input fields matches Excel data")
    public void loginDataInExcelValidation() {
        Map<String, String> dataMap = getDataMap();
        LandingPage landingPage = new LandingPage(driver);
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
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
        LandingPage landingPage = new LandingPage(driver);

        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));

        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));

        upcomingGamesPage.getUpcomingGamesList();

        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);

        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        wait.until(ExpectedConditions.elementToBeClickable(modalElement));

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
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
        LandingPage landingPage = new LandingPage(driver);

        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));

        landingPage.logMaskedSensitiveInfo(dataMap.get(EMAIL), dataMap.get(PASSWORD));

        upcomingGamesPage.getUpcomingGamesList();

        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotNull(gameElement);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);

        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        wait.until(ExpectedConditions.elementToBeClickable(modalElement));

        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.logMaskedSensitiveInfo(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(String.valueOf(dataMap.get(NUMBER_OF_TEAMMATES))));
        assertTrue(dataMap.containsValue(registerGamePage.getTeamName()));
        assertTrue(dataMap.containsValue(registerGamePage.getNumberOfPlayers()));
        registerGamePage.clickFurtherButton();
    }
}