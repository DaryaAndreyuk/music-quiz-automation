import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://wro.mzgb.net/");
        driver.manage().window().maximize();
    }

    @Test
    public void testTitle() {
        assertEquals(TITLE, driver.getTitle());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
