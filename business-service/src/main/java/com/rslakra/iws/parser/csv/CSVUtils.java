package com.rslakra.iws.parser.csv;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rohtash Lakra (rlakra)
 * @created 9/27/22 4:44 PM
 */
public enum CSVUtils {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtils.class);
    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String CSV_MEDIA_TYPE = "application/csv";

    // CSV_WRITER_FORMAT
    public static final CSVFormat CSV_WRITER_FORMAT = CSVFormat.DEFAULT.builder()
        .setQuoteMode(QuoteMode.MINIMAL)
        .build();

}
