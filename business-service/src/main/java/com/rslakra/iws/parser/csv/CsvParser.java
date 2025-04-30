package com.rslakra.iws.parser.csv;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.iws.parser.Parser;
import org.apache.commons.csv.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 4/5/23 4:29 PM
 */
public interface CsvParser<T> extends Parser<T> {

    String CSV_CONTENT_TYPE = "text/csv";
    String CSV_FILE_TYPE = "csv";
    String CSV_MEDIA_TYPE = "application/csv";

    CSVFormat CSV_WRITER_FORMAT = CSVFormat.DEFAULT.builder()
            .setQuoteMode(QuoteMode.MINIMAL)
            .get();


    /**
     * @param file
     * @return
     */
    static boolean isCSVFile(MultipartFile file) {
        return Parser.isTypeOf(file, CSV_CONTENT_TYPE);
    }

    /**
     * @param fileType
     * @return
     */
    static boolean isCSVFileType(final String fileType) {
        return CSV_FILE_TYPE.equalsIgnoreCase(fileType);
    }

    /**
     * @param headers
     * @return
     */
    default CSVFormat getReaderFormat(final String... headers) {
        return CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .setQuoteMode(QuoteMode.ALL)
                .get();
    }

    /**
     * Writes the list of <code>T</code> objects to the <code>OutputStream</code>.
     *
     * @param tList
     * @return
     */
    @Override
    default OutputStream writeStream(List<T> tList) {
        return null;
    }

    /**
     * @param csvRecord
     * @return
     */
    T readCSVRecord(CSVRecord csvRecord);

    /**
     * @param inputStream
     * @return
     */
    default List<T> readCSVStream(InputStream inputStream) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = CSVParser.parse(fileReader, getReaderFormat(getReadHeaders()))) {
            final List<T> tList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                tList.add(readCSVRecord(csvRecord));
            }

            return tList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * @param tList
     * @return
     */
    default InputStreamResource buildCSVResourceStream(final List<T> tList) {
        try (ByteArrayOutputStream csvByteStream = new ByteArrayOutputStream();
             final CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(csvByteStream), CSV_WRITER_FORMAT);) {
            // add headers row
            if (BeanUtils.isNotEmpty(getWriteHeaders())) {
                csvPrinter.printRecord(getWriteHeaders());
            }

            // add contents
            if (BeanUtils.isNotEmpty(tList)) {
                tList.forEach(item -> {
                    try {
                        csvPrinter.printRecord(buildRowCells(item));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }

            csvPrinter.flush();
            return Parser.newInputStreamResource(csvByteStream.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException("Failed to import data to CSV file: " + ex.getMessage());
        }
    }

}
