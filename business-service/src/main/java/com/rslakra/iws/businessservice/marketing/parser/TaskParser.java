package com.rslakra.iws.businessservice.marketing.parser;

import com.devamatre.appsuite.core.BeanUtils;
import com.rslakra.iws.businessservice.marketing.persistence.entity.Task;
import com.rslakra.iws.parser.AbstractParser;
import com.rslakra.iws.parser.Parser;
import com.rslakra.iws.parser.csv.CsvParser;
import com.rslakra.iws.parser.excel.ExcelParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 4/5/23 4:55 PM
 */
public class TaskParser extends AbstractParser<Task> implements ExcelParser<Task>, CsvParser<Task> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskParser.class);

    public static final String CSV_DOWNLOAD_FILE_NAME = "tasks.csv";
    public static final String EXCEL_DOWNLOAD_FILE_NAME = "tasks.xlsx";
    public static final String[] HEADERS = {
        "id", "taskId", "title", "priority", "startDate", "endDate", "status", "description"
    };

    /**
     * @return
     */
    @Override
    public String[] getReadHeaders() {
        return HEADERS;
    }

    /**
     * @return
     */
    @Override
    public String[] getWriteHeaders() {
        return HEADERS;
    }

    /**
     * @return
     */
    @Override
    public String getSheetName() {
        return Task.class.getSimpleName();
    }

    /**
     * @param rowCells
     * @return
     */
    @Override
    public Task readCells(Iterator<Cell> rowCells) {
        Task task = new Task();
        int cellIndex = 0;
        while (rowCells.hasNext()) {
            Cell currentCell = rowCells.next();
            // "id", "userId", "title", "priority", "startDate", "endDate", "status", "description"
            switch (cellIndex) {
                case 0:
//                    task.setId((long) currentCell.getNumericCellValue());
                    break;
                case 1:
                    task.setUserId(Parser.asType(currentCell.getStringCellValue(), Long.class));
                    break;

                case 2:
                    task.setTitle(currentCell.getStringCellValue());
                    break;

                case 3:
                    task.setPriority(Parser.asType(currentCell.getStringCellValue(), Integer.class));
                    break;

                case 4:
                    task.setStartDate(currentCell.getDateCellValue());
                    break;

                case 5:
                    task.setEndDate(currentCell.getDateCellValue());
                    break;

                case 6:
                    task.setStatus(currentCell.getStringCellValue());
                    break;

                case 7:
                    task.setDescription(currentCell.getStringCellValue());
                    break;

                default:
                    break;
            }

            cellIndex++;
        }

        return task;
    }

    /**
     * @param csvRecord
     * @return
     */
    @Override
    public Task readCSVRecord(CSVRecord csvRecord) {
        LOGGER.debug("+readCSVRecord({})", csvRecord);
        Task task = new Task();
        // "id", "userId", "title", "priority", "startDate", "endDate", "status", "description"
        task.setUserId(Parser.asType(csvRecord.get(1), Long.class)); // userId
        task.setTitle(csvRecord.get(2)); // title
        task.setPriority(Parser.asType(csvRecord.get(3), Integer.class)); // priority
        task.setStartDate(Parser.asType(csvRecord.get(4), Date.class)); // startDate
        task.setEndDate(Parser.asType(csvRecord.get(5), Date.class)); // endDate
        task.setStatus(csvRecord.get(6)); // status
        task.setDescription(csvRecord.get(7)); // description
        LOGGER.debug("-readCSVRecord(), task:{}", task);
        return task;
    }

    /**
     * @param task
     * @return
     */
    @Override
    public List<String> buildRowCells(Task task) {
        List<String> taskContents = new LinkedList<>();
        // "id", "userId", "title", "priority", "startDate", "endDate", "status", "description"
        taskContents.add(BeanUtils.toString(task.getId()));
        taskContents.add(BeanUtils.toString(task.getUserId()));
        taskContents.add(task.getTitle());
        taskContents.add(BeanUtils.toString(task.getPriority()));
        taskContents.add(BeanUtils.toString(task.getStartDate()));
        taskContents.add(BeanUtils.toString(task.getEndDate()));
        taskContents.add(task.getStatus());
        taskContents.add(task.getDescription());
        return taskContents;
    }

}
