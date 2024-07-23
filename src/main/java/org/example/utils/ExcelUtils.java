package org.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ExcelUtils {
    public static Map<String, String> dataMap = new HashMap<>();

    public static Map<String, String> getExcelDataToMap(String filePath) {

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
    public static boolean isInExcel(String value) {
        return dataMap.containsValue(value);
    }

    private static String normalizeNumber(String number) {
        return number.replaceAll("[^0-9]", "");
    }

    private static String formatCell(Cell cell) {
        if (cell == null) {
            return "";
        }
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

    public static String getPathToResourceFile(String fileName) {
        String projectDir = System.getProperty("user.dir");
        Path filePath = Paths.get(projectDir, "src", "test", "resources", "data", fileName);
        return filePath.toAbsolutePath().toString();
    }
}
