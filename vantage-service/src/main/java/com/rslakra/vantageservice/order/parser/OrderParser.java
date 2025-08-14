package com.rslakra.vantageservice.order.parser;

import com.rslakra.appsuite.spring.parser.AbstractParser;
import com.rslakra.appsuite.spring.parser.csv.CsvParser;
import com.rslakra.appsuite.spring.parser.excel.ExcelParser;
import com.rslakra.vantageservice.order.persistence.entity.Order;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
public class OrderParser extends AbstractParser<Order> implements ExcelParser<Order>, CsvParser<Order> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderParser.class);
    public static final String CSV_DOWNLOAD_FILE_NAME = "orders.csv";
    public static final String EXCEL_DOWNLOAD_FILE_NAME = "orders.xlsx";
    
    public static final String[] HEADERS = {
            "id", "email", "firstName", "middleName", "lastName"
    };
    
    /**
     * @return
     */
    @Override
    public String getUploadFileName() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public String getDownloadFileName() {
        return null;
    }
    
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
     * Returns the name of the Excel sheet.
     *
     * @return
     */
    @Override
    public String getSheetName() {
        return Order.class.getSimpleName();
    }
    
    /**
     * @param rowCells
     * @return
     */
    @Override
    public Order readCells(Iterator<Cell> rowCells) {
        Order order = new Order();
        int cellIndex = 0;
        while (rowCells.hasNext()) {
            Cell currentCell = rowCells.next();
            switch (cellIndex) {
                case 0:
//                    order.setId((long) currentCell.getNumericCellValue());
                    break;
                case 1:
                    order.setRefNumber(currentCell.getStringCellValue());
                    break;
                
                case 2:
                    order.setOrderDate(currentCell.getStringCellValue());
                    break;
                
                default:
                    break;
            }
            
            cellIndex++;
        }
        
        return order;
    }
    
    /**
     * @param csvRecord
     * @return
     */
    @Override
    public Order readCSVRecord(CSVRecord csvRecord) {
        LOGGER.debug("+buildCSVRecord({})", csvRecord);
        Order order = new Order();
//        order.setCustomerId(csvRecord.get(1)); // customerId
        order.setRefNumber(csvRecord.get(2)); // refNumber
        order.setOrderDate(csvRecord.get(3)); // orderDate
        LOGGER.debug("-buildCSVRecord(), order:{}", order);
        return order;
    }
    
    /**
     * @param order
     * @return
     */
    @Override
    public List<String> buildRowCells(Order order) {
        List<String> orderContents = new LinkedList<>();
        orderContents.add(order.getId().toString());
        orderContents.add(order.getCustomerId().toString());
        orderContents.add(order.getRefNumber());
        orderContents.add(order.getOrderDate());
        orderContents.add(order.getStatus().name());
        return orderContents;
    }
    
}
