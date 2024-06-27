import basecomponents.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleTest extends BaseTest {

    @Test
    public void testTitle() {
        assertEquals(TITLE, driver.getTitle());
    }
}
