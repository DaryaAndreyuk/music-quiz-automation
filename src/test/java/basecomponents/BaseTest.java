package basecomponents;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
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
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl == null) {
            throw new RuntimeException("SELENIUM_REMOTE_URL environment variable is not set.");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Добавляем headless режим
        options.addArguments("--disable-gpu"); // Отключаем GPU, так как он не нужен в headless режиме
        options.addArguments("--no-sandbox"); // Отключаем sandbox для предотвращения проблем с правами доступа
        options.addArguments("--disable-dev-shm-usage"); // Используем /tmp вместо /dev/shm
        options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
        try {
            driver = new RemoteWebDriver(new URL(remoteUrl), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
        }
        driver.manage().window().maximize();
        return driver;
    }

    @AfterEach
    public void tearDown() {
        driver = driverThreadLocal.get();
        if (driver != null) {
            driver.manage().logs().get("browser").forEach(logEntry -> System.out.println(logEntry.getMessage()));
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