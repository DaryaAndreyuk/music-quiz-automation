package basecomponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.example.pages.LandingPage;
import org.example.pages.RegisterGamePage;
import org.example.pages.UpcomingGamesPage;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(AllureScreenshotExtension.class)
public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;
    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeEach
    public void setUp() {
        driver = initializeChromeDriver();
        driverThreadLocal.set(driver);
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
        driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("initialize register page")
    public RegisterGamePage initializeRegisterPage() {
        String filePath = ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE);
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(filePath);
        landingPage.closeCookieAlert();
        UpcomingGamesPage upcomingGamesPage = landingPage.loginApplication(dataMap.get(EMAIL), dataMap.get(PASSWORD));
        upcomingGamesPage.getUpcomingGamesList();
        WebElement gameElement = upcomingGamesPage.getGameByType(MOZGO_QUIZ_GAME_TYPE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(gameElement));
        assertNotEquals(null, gameElement);
        return upcomingGamesPage.clickOnRegisterButton(gameElement);
    }

    @Step("perform Login And Check Error")
    public void performLoginAndCheckError(String email, String password, String expectedErrorMessage) {
        landingPage.closeCookieAlert();
        landingPage.loginApplication(email, password);
        assertEquals(expectedErrorMessage, landingPage.getErrorMessage());
    }

    public WebDriver getDriver() {
        return driver;
    }
}