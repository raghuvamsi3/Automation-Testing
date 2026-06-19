package com.sponsorship.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelUtils - Read/write Excel files using Apache POI.
 * Used for data-driven testing and updating RTM/Test Case/Defect documents.
 */
public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    /**
     * Opens an Excel workbook.
     */
    public ExcelUtils(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            this.workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open Excel file: " + filePath, e);
        }
    }

    /**
     * Sets the active sheet by name.
     */
    public ExcelUtils setSheet(String sheetName) {
        this.sheet = workbook.getSheet(sheetName);
        if (this.sheet == null) {
            throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
        }
        return this;
    }

    /**
     * Sets the active sheet by index.
     */
    public ExcelUtils setSheet(int index) {
        this.sheet = workbook.getSheetAt(index);
        return this;
    }

    /**
     * Gets the total number of rows (including header).
     */
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * Gets the total number of columns in the first row.
     */
    public int getColumnCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    /**
     * Gets cell value as String.
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    /**
     * Gets cell value by row number and column header name.
     */
    public String getCellData(int rowNum, String columnName) {
        int colNum = getColumnIndex(columnName);
        return getCellData(rowNum, colNum);
    }

    /**
     * Gets the column index for a given header name.
     */
    private int getColumnIndex(String columnName) {
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            if (headerRow.getCell(i).getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new RuntimeException("Column '" + columnName + "' not found in sheet");
    }

    /**
     * Sets cell value (creates cell if needed).
     */
    public void setCellData(int rowNum, int colNum, String value) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);
    }

    /**
     * Sets cell value by column name.
     */
    public void setCellData(int rowNum, String columnName, String value) {
        int colNum = getColumnIndex(columnName);
        setCellData(rowNum, colNum, value);
    }

    /**
     * Reads all data as a 2D list (excluding header row).
     */
    public List<List<String>> getAllData() {
        List<List<String>> data = new ArrayList<>();
        int rowCount = getRowCount();
        int colCount = getColumnCount();

        for (int i = 1; i < rowCount; i++) { // Skip header
            List<String> rowData = new ArrayList<>();
            for (int j = 0; j < colCount; j++) {
                rowData.add(getCellData(i, j));
            }
            data.add(rowData);
        }
        return data;
    }

    /**
     * Saves changes to the Excel file.
     */
    public void save() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Excel file: " + filePath, e);
        }
    }

    /**
     * Closes the workbook.
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to close workbook: " + e.getMessage());
        }
    }

    /**
     * Gets all sheet names in the workbook.
     */
    public List<String> getSheetNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            names.add(workbook.getSheetName(i));
        }
        return names;
    }
}
