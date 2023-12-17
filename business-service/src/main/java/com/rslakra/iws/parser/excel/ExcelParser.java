package com.rslakra.iws.parser.excel;

import com.devamatre.appsuite.core.BeanUtils;
import com.rslakra.iws.parser.Parser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rohtash Lakra
 * @created 4/5/23 4:29 PM
 */
public interface ExcelParser<T> extends Parser<T> {

    Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);
    String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    String EXCEL_FILE_TYPE = "excel";
    String EXCEL_MEDIA_TYPE = "application/vnd.ms-excel";

    /**
     * @param file
     * @return
     */
    static boolean isExcelFile(MultipartFile file) {
        return Parser.isTypeOf(file, EXCEL_CONTENT_TYPE);
    }

    /**
     * @param fileType
     * @return
     */
    static boolean isExcelFileType(final String fileType) {
        return EXCEL_FILE_TYPE.equalsIgnoreCase(fileType);
    }

    /**
     * Returns the name of the Excel sheet.
     *
     * @return
     */
    String getSheetName();

    /**
     * Returns the list of <code>T</code> object after parsing the <code>InputStream</code> objects.
     *
     * @param inputStream
     * @return
     */
    @Override
    default List<T> readStream(InputStream inputStream) {
        LOGGER.debug("+readStream({})", inputStream);
        try {
            final List<T> listObjects = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(getSheetName());
            Iterator<Row> rows = sheet.iterator();

            final AtomicInteger rowNumber = new AtomicInteger(0);
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber.get() == 0) {
                    rowNumber.incrementAndGet();
                    continue;
                }

                // read's current row's cells
                listObjects.add(readCells(currentRow.iterator()));
            }

            workbook.close();

            LOGGER.debug("-readStream(), listObjects: {}", listObjects);
            return listObjects;
        } catch (IOException ex) {
            LOGGER.debug("Error while parsing Excel file! Error: {}, {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to parse Excel file: " + ex.getMessage());
        }
    }

    /**
     * @param rowCells
     * @return
     */
    T readCells(final Iterator<Cell> rowCells);

    /**
     * Adds the row of <code>headers</code> to the provided <code>sheet</code>.
     *
     * @param sheet
     * @param headers
     */
    default void addHeaders(Sheet sheet, String[] headers) {
        BeanUtils.assertNonNull(sheet, "The sheet should not be null!");
        // Headers Row
        if (BeanUtils.isNotEmpty(headers)) {
            Row headerRow = sheet.createRow(0);
            for (int column = 0; column < headers.length; column++) {
                Cell cell = headerRow.createCell(column);
                cell.setCellValue(headers[column]);
            }
        }
    }

    /**
     * Adds the <code>T</code> object values as cells of the provided <code>row</code>.
     *
     * @param row
     * @param t
     */
    default void addCells(Row row, T t) {
        BeanUtils.assertNonNull(row, "The row object should not be null!");
        BeanUtils.assertNonNull(t, "The T object should not be null!");
        final AtomicInteger cellIndex = new AtomicInteger(0);
        buildRowCells(t).forEach(item -> row.createCell(cellIndex.getAndIncrement()).setCellValue(item));
    }

    /**
     * @param sheet
     * @param tList
     */
    default void addRows(Sheet sheet, List<T> tList) {
        LOGGER.debug("+addRows({}, {})", sheet, tList);
        BeanUtils.assertNonNull(sheet, "The sheet should not be null!");
        // fill rows with the provided data
        if (BeanUtils.isNotEmpty(tList)) {
            final AtomicInteger rowIndex = new AtomicInteger(0);
            tList.forEach(item -> addCells(sheet.createRow(rowIndex.incrementAndGet()), item));
        }
        LOGGER.debug("-addRows(), physicalNumberOfRows: {}", sheet.getPhysicalNumberOfRows());
    }

    /**
     * @param excelSheetName
     * @param sheetHeaders
     * @param tList
     * @return
     */
    default InputStreamResource buildStreamResources(String excelSheetName, String[] sheetHeaders, List<T> tList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteStream = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(excelSheetName);
            // Headers Row
            addHeaders(sheet, sheetHeaders);
            // add rows to the sheet
            addRows(sheet, tList);
            // write sheet to byte stream
            workbook.write(byteStream);
            return new InputStreamResource(new ByteArrayInputStream(byteStream.toByteArray()));
        } catch (IOException ex) {
            throw new RuntimeException("Failed to import data to Excel file: " + ex.getMessage());
        }
    }

    /**
     * Builds the list of <code>T</code> objects as stream.
     *
     * @param tList
     * @return
     */
    default InputStreamResource buildStreamResources(final List<T> tList) {
        return buildStreamResources(getSheetName(), getWriteHeaders(), tList);
    }

}
