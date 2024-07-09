package basecomponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pageobjects.LandingPage;
import org.example.pageobjects.RegisterGamePage;
import org.example.pageobjects.UpcomingGamesPage;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    @BeforeEach
    public void setUp() {
        driver = initializeChromeDriver();
        driver.get(MOZGO_QUIZ_URL);
        landingPage = new LandingPage(driver);
    }

    public WebDriver initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public RegisterGamePage initializeRegisterPage() {
        String filePath = ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE);
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(filePath);
        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        assertNotEquals(null, gameElement);
        return upcomingGamesPage.clickOnRegisterButton(gameElement);
    }

    public void performLoginAndCheckError(String email, String password, String expectedErrorMessage) {
        landingPage.closeCookieAlert();
        landingPage.loginApplication(email, password);
        assertEquals(expectedErrorMessage, landingPage.getErrorMessage());
    }
}

