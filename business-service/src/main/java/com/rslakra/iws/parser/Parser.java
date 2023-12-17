package com.rslakra.iws.parser;

import com.devamatre.appsuite.core.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 4/5/23 4:26 PM
 */
public interface Parser<T> {

    /**
     * Returns the value as type of <code>Class<T></code> type.
     *
     * @param text
     * @param tClass
     * @param <T>
     * @return
     */
    static <T> T asType(String text, Class<T> tClass) {
        if (BeanUtils.isNotNull(text) && BeanUtils.isNotNull(tClass)) {
            if (BeanUtils.isTypeOf(tClass, BigDecimal.class)) {
                return (T) new BigDecimal(text);
            } else if (BeanUtils.isTypeOf(tClass, BigInteger.class)) {
                return (T) new BigInteger(text);
            } else if (BeanUtils.isTypeOf(tClass, Double.class)) {
                return (T) Double.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Float.class)) {
                return (T) Float.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Long.class)) {
                return (T) Long.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Integer.class)) {
                return (T) Integer.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Short.class)) {
                return (T) Short.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Byte.class)) {
                return (T) Byte.valueOf(text);
            } else if (BeanUtils.isTypeOf(tClass, Date.class)) {
                return (T) new Date(text);
            }
        }

        return (T) null;
    }

    /**
     * Returns true if the <code>file</code> is type of <code>contentType</code> otherwise false.
     *
     * @param file
     * @param contentType
     * @return
     */
    static boolean isTypeOf(MultipartFile file, final String contentType) {
        return BeanUtils.isNotNull(file) && BeanUtils.equals(contentType, file.getContentType());
    }

    /**
     * Returns the mediaType object.
     *
     * @param mediaType
     * @return
     */
    static MediaType getMediaType(final String mediaType) {
        return MediaType.parseMediaType(mediaType);
    }

    /**
     * Returns the <code>ContentDisposition</code> of the provided <code>fileName</code>.
     *
     * @param fileName
     * @return
     */
    static String getContentDisposition(final String fileName) {
        BeanUtils.assertNonNull(fileName, "fileName should not be null or empty!");
        return ("attachment; filename=" + fileName);
    }

    /**
     * @param contentDisposition
     * @param mediaType
     * @param inputStreamResource
     * @return
     */
    static ResponseEntity<Resource> buildOKResponse(String contentDisposition, MediaType mediaType,
                                                    InputStreamResource inputStreamResource) {
        if (BeanUtils.isNotEmpty(contentDisposition) && BeanUtils.isNotNull(mediaType) && BeanUtils.isNotNull(
            inputStreamResource)) {
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(mediaType)
                .body(inputStreamResource);
        }

        return null;
    }

    /**
     * Returns the <code>InputStreamResource</code> from the provided <code>byte[]</code> object.
     *
     * @param dataBytes
     * @return
     */
    static InputStreamResource newInputStreamResource(byte[] dataBytes) {
        return new InputStreamResource(new ByteArrayInputStream(dataBytes));
    }

    /**
     * @return
     */
    String[] getReadHeaders();

    /**
     * @return
     */
    String[] getWriteHeaders();

    /**
     * Returns the list of <code>T</code> object after parsing the <code>InputStream</code> objects.
     *
     * @param inputStream
     * @return
     */
    List<T> readStream(InputStream inputStream);

    /**
     * Returns the value of the cells of the row of the <code>T</code> object.
     *
     * @param t
     * @return
     */
    List<String> buildRowCells(T t);

    /**
     * Writes the list of <code>T</code> objects to the <code>OutputStream</code>.
     *
     * @param ts
     * @return
     */
    OutputStream writeStream(List<T> ts);

}
