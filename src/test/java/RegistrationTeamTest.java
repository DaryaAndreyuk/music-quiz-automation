import basecomponents.BaseTest;
import org.example.pageobjects.LandingPage;
import org.example.pageobjects.RegisterGamePage;
import org.example.pageobjects.UpcomingGamesPage;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.utils.ExcelUtils;

public class RegistrationTeamTest extends BaseTest {

    @Test
    public void registerTeamValidation() {

        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
        LandingPage landingPage = new LandingPage(driver);
        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.closeCookieAlert();
        upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);

        assertNotNull(gameElement);
        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();

        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(dataMap.get(NUMBER_OF_TEAMMATES)));
        registerGamePage.clickFurtherButton();

        registerGamePage.waitForWebElementToAppear(registerGamePage.getFinalRegButton());
        assertTrue(registerGamePage.getFinalRegButton().isDisplayed() && registerGamePage.getFinalRegButton().isEnabled());
        // registerGamePage.registerTeam();
    }

    @Test
    public void loginDataInExcelValidation() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
        LandingPage landingPage = new LandingPage(driver);
        landingPage.closeCookieAlert();
        landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        assertTrue(dataMap.containsValue(landingPage.getEmail()));
        assertTrue(dataMap.containsValue(landingPage.getPassword()));
    }

    @Test
    public void personalDataInExcelValidation() {
        RegisterGamePage registerGamePage = initializeRegisterPage();
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));

        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        assertTrue(dataMap.containsValue(registerGamePage.getFio()));
        assertTrue(dataMap.containsValue(registerGamePage.getPhone()));
        assertTrue(dataMap.containsValue(registerGamePage.getEmail()));
        registerGamePage.clickFurtherButton();
    }

    @Test
    public void teamDataInExcelValidation() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
        RegisterGamePage registerGamePage = initializeRegisterPage();

        registerGamePage.fillPersonalData(dataMap.get(NAME), dataMap.get(PHONE), dataMap.get(EMAIL));
        registerGamePage.clickFurtherButton();
        registerGamePage.fillTeamData(dataMap.get(TEAM), Integer.parseInt(String.valueOf(dataMap.get(NUMBER_OF_TEAMMATES))));
        assertTrue(dataMap.containsValue(registerGamePage.getTeamName()));
        assertTrue(dataMap.containsValue(registerGamePage.getNumberOfPlayers()));
        registerGamePage.clickFurtherButton();
    }
}