package basecomponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.pageobjects.LandingPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    public WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = initializeChromeDriver();
        driver.get("https://wro.mzgb.net/");
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

    public Map<String, String> getExcelDataToMap(String filePath) {
        Map<String, String> dataMap = new HashMap<String, String>();

        try (InputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String key = formatCell(row.getCell(0));
                String value = formatCell(row.getCell(1));
                dataMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    private String normalizeNumber(String number) {
        return number.replaceAll("[^0-9]", "");
    }

    private String formatCell(Cell cell) {
        DataFormatter dataFormatter = new DataFormatter();
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                String cellValue = dataFormatter.formatCellValue(cell);
                yield normalizeNumber(cellValue);
            }
            default -> "";
        };
    }

    public String getPathToResourceFile() {
        String projectDir = System.getProperty("user.dir");
        Path filePath = Paths.get(projectDir, "src", "test", "resources", "data", "Sheet.xlsx");
        return filePath.toAbsolutePath().toString();
    }

}

