package basecomponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.example.utils.Constants.MOZGO_QUIZ_URL;

public class BaseTest {

    public WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = initializeChromeDriver();
        driver.get(MOZGO_QUIZ_URL);
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
}

