import basecomponents.BaseTest;
import org.example.pageobjects.LandingPage;
import org.example.pageobjects.RegisterGamePage;
import org.example.pageobjects.UpcomingGamesPage;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.example.utils.Constants.MOZGO_QUIZ_GAME_TYPE;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.utils.ExcelUtils;

public class RegistrationTeamTest extends BaseTest {

    @Test
    public void checkTeamRegistration() {

        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile());

        LandingPage landingPage = new LandingPage(driver);
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get("Email"), dataMap.get("Password"));

        List<WebElement> games = upcomingGamesPage.getUpcomingGamesList();

        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);

        assertNotEquals(null, gameElement);
        RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
        registerGamePage.fillPersonalData(dataMap.get("Name"), dataMap.get("Phone"), dataMap.get("Email"));
        registerGamePage.fillTeamData(dataMap.get("Team"), Integer.parseInt(dataMap.get("Number of teammates")));
        assertTrue(registerGamePage.getFinalRegButton().isDisplayed() && registerGamePage.getFinalRegButton().isEnabled());
        // registerGamePage.registerTeam();
    }
}