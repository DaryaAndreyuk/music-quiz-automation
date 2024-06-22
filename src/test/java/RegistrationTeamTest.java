import basecomponents.BaseTest;
import org.example.pageobjects.LandingPage;
import org.example.pageobjects.RegisterGamePage;
import org.example.pageobjects.UpcomingGamesPage;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class RegistrationTeamTest extends BaseTest {

    @Test
    public void checkTeamRegistration() {

        Map<String, String> dataMap = getExcelDataToMap(getPathToResourceFile());

        LandingPage landingPage = new LandingPage(driver);
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get("Email"), dataMap.get("Password"));

        List<WebElement> games = upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType("MozgoQuiz");

        if (gameElement != null) {
            RegisterGamePage registerGamePage = upcomingGamesPage.clickOnRegisterButton(gameElement);
            registerGamePage.fillPersonalData(dataMap.get("Name"), dataMap.get("Phone"), dataMap.get("Email"));
            registerGamePage.fillTeamData(dataMap.get("Team"), Integer.parseInt(dataMap.get("Number of teammates")));
            assertTrue(true);
            // registerGamePage.registerTeam();
        } else {
            System.out.println("No upcoming games");
        }
    }
}