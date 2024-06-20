import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleTest {

    private WebDriver driver;
    public static String EXPECTED_TITLE = "MozgoQuiz в Вроцлаве - интеллектуальный квиз с вопросами и ответами";


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "I:\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testTitle() {
        driver.get("https://wro.mzgb.net/");
        driver.manage().window().maximize();
        // Проверка заголовка страницы
        assertEquals(EXPECTED_TITLE, driver.getTitle());

    }

    @AfterEach
    public void tearDown() {
        // Закрытие браузера после выполнения каждого теста
        if (driver != null) {
            driver.quit();
        }
    }
}
