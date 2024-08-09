import basecomponents.BaseTest;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static org.example.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExcelIntegrationTest extends BaseTest {

    @Test
    @Tag("negative")
    @DisplayName("Verify handling when XLSX file for parsing is missing")
    public void testNoFile() {
        String missingFilePath = ExcelUtils.getPathToResourceFile(NO_SHEET_DATA_FILE);

        assertThrows(IOException.class, () -> {
            try (FileInputStream fis = new FileInputStream(missingFilePath)) {
                WorkbookFactory.create(fis);
            }
        });
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify handling when XLSX file for parsing is empty")
    public void testEmptyFile() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(EMPTY_SHEET_DATA_FILE));
        performLoginAndCheckError(EMAIL_INCORRECT_FORMAT_EXTENDED_MESSAGE, dataMap.get(PASSWORD), dataMap.get(EMAIL));
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify handling when XLSX file for parsing has missing required fields")
    public void testMissingRequiredField() {
        Map<String, String> dataMap = ExcelUtils.getExcelDataToMap(ExcelUtils.getPathToResourceFile(MISSING_REQUIRED_SHEET_DATA_FILE));
        assertTrue(dataMap.get(PASSWORD).isEmpty());
        performLoginAndCheckError(PASSWORD_IS_REQUIRED_MESSAGE, dataMap.get(PASSWORD), dataMap.get(EMAIL));
    }
}