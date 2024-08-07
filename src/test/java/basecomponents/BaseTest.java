package basecomponents;
import org.example.pages.LandingPage;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AllureScreenshotExtension.class)
public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public LandingPage landingPage;
    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeEach
    public void setUp() {
        driver = initializeChromeDriver();
        driverThreadLocal.set(driver);
        driver.get(MOZGO_QUIZ_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        landingPage = new LandingPage(driver);
    }

    public WebDriver initializeChromeDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl == null) {
            throw new RuntimeException("SELENIUM_REMOTE_URL environment variable is not set.");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Add headless mode
        options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
        options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
        options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
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

    public static Map<String, String> getDataMap() {
        return ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(SHEET_DATA_FILE));
    }

    public void performLoginAndCheckError(String email, String password, String expectedErrorMessage) {
        landingPage.closeCookieAlert();
        landingPage.loginApplication(email, password);
        if (!email.isEmpty() || !password.isEmpty()) {
            landingPage.logMaskedSensitiveInfo(email, password);
        }
        assertEquals(expectedErrorMessage, landingPage.getErrorMessage());
    }

    public WebDriver getDriver() {
        return driver;
    }
}