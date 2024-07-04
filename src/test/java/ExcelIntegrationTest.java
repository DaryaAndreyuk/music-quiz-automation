import basecomponents.BaseTest;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;


public class ExcelIntegrationTest extends BaseTest {

    @Test
    public void testNoFile() {
        String missingFilePath = ExcelUtils.getPathToResourceFile(NO_SHEET_DATA_FILE);

        assertThrows(IOException.class, () -> {
            try (FileInputStream fis = new FileInputStream(missingFilePath)) {
                WorkbookFactory.create(fis);
            }
        });
    }

    @Test
    public void testEmptyFile() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(EMPTY_SHEET_DATA_FILE));
        performLoginAndCheckError(dataMap.get(EMAIL), dataMap.get(PASSWORD), "Поле E-mail имеет неверный формат. (and 1 more error)");
    }

    @Test
    public void testMissingRequiredField() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(MISSING_REQUIRED_SHEET_DATA_FILE));
        assertTrue(dataMap.get(PASSWORD).isEmpty());
        performLoginAndCheckError(dataMap.get(EMAIL), dataMap.get(PASSWORD), "Поле Пароль обязательно для заполнения.");
    }
}