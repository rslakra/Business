package com.rslakra.vantageservice.order.controller.rest;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.controller.rest.AbstractRestController;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.parser.Parser;
import com.rslakra.appsuite.spring.parser.csv.CsvParser;
import com.rslakra.appsuite.spring.parser.excel.ExcelParser;
import com.rslakra.vantageservice.order.filter.OrderFilter;
import com.rslakra.vantageservice.order.parser.OrderParser;
import com.rslakra.vantageservice.order.persistence.entity.Order;
import com.rslakra.vantageservice.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@RestController
@RequestMapping("${restPrefix}/orders")
//@Tag(name = "Order Service")
public class OrderController extends AbstractRestController<Order, Long> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    
    private final OrderService orderService;
    private OrderParser orderParser;
    
    /**
     * @param orderService
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.orderParser = new OrderParser();
    }
    
    /**
     * Returns the list of <code>T</code> objects.
     *
     * @return
     */
    @GetMapping
//    @Operation(summary = "Get all orders", description = "Get all orders",
//        tags = {"Order Service"},
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Get the orders successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class))))
//        })
    @Override
    public List<Order> getAll() {
        return orderService.getAll();
    }
    
    /**
     * Returns the list of <code>T</code> filters objects.
     *
     * @param allParams
     * @return
     */
    @GetMapping("/filter")
    @Override
    public List<Order> getByFilter(@RequestParam Map<String, Object> allParams) {
        LOGGER.debug("+getByFilter({})", allParams);
        List<Order> orders = Collections.emptyList();
        OrderFilter userFilter = new OrderFilter(allParams);
        if (userFilter.hasKey(OrderFilter.ID)) {
            orders = Arrays.asList(orderService.getById(userFilter.getLong(OrderFilter.ID)));
        } else {
            orders = orderService.getAll();
        }
        
        LOGGER.debug("-getByFilter(), orders: {}", orders);
        return orders;
    }
    
    /**
     * Returns the <code>Page<T></code> list of objects filtered with <code>allParams</code>.
     *
     * @param allParams @return
     * @param pageable
     */
    @GetMapping("/pageable")
    @Override
    public Page<Order> getByFilter(Map<String, Object> allParams, Pageable pageable) {
        return orderService.getByFilter(null, pageable);
    }
    
    /**
     * @param filter
     * @return
     */
    @Override
    public List<Order> getByFilter(Filter filter) {
        return null;
    }
    
    /**
     * @param filter
     * @param pageable
     * @return
     */
    @Override
    public Page<Order> getByFilter(Filter filter, Pageable pageable) {
        return null;
    }
    
    /**
     * Creates the <code>T</code> type object.
     *
     * @param user
     * @return
     */
    @PostMapping
    @ResponseBody
//    @Operation(summary = "Create new user", description = "Create new user",
//        tags = {"Order Service"},
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Creates the user successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class))))
//        })
    @Override
    public ResponseEntity<Order> create(
//        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create new user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)), required = true)
            @Validated @RequestBody Order user) {
        user = orderService.create(user);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Creates the list of <code>T</code> type objects.
     *
     * @param orders
     * @return
     */
    @PostMapping("/batch")
    @ResponseBody
//    @Operation(summary = "Create new user", description = "Create new user",
//        tags = {"Order Service"},
//        responses = {
//            @ApiResponse(responseCode = "200", description = "Creates the user successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class))))
//        })
    @Override
    public ResponseEntity<List<Order>> create(@Validated @RequestBody List<Order> orders) {
        orders = orderService.create(orders);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Updates the <code>T</code> type object.
     *
     * @param user
     * @return
     */
    @PutMapping
    @Override
    public ResponseEntity<Order> update(@Validated @RequestBody Order user) {
        user = orderService.update(user);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Updates the list of <code>T</code> type objects.
     *
     * @param orders
     * @return
     */
    @PutMapping("/batch")
    @Override
    public ResponseEntity<List<Order>> update(@Validated @RequestBody List<Order> orders) {
        orders = orderService.update(orders);
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Deletes the <code>T</code> type object by <code>id</code>.
     *
     * @param idOptional
     * @return
     */
    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<Payload> delete(@PathVariable(value = "userId") Optional<Long> idOptional) {
        validate(idOptional);
        orderService.delete(idOptional.get());
        Payload payload = Payload.newBuilder()
                .withDeleted(Boolean.TRUE)
                .withMessage("Record with id:%d deleted successfully!", idOptional.get());
        return ResponseEntity.ok(payload);
    }
    
    /**
     * Uploads the <code>file</code> of objects.
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @Override
    public ResponseEntity<Payload> upload(@RequestParam("file") MultipartFile file) {
        Payload payload = Payload.newBuilder();
        try {
            List<Order> userList = null;
            if (CsvParser.isCSVFile(file)) {
                userList = orderParser.readCSVStream(file.getInputStream());
            } else if (ExcelParser.isExcelFile(file)) {
                userList = orderParser.readStream(file.getInputStream());
            }
            
            // check the user list is available
            if (Objects.nonNull(userList)) {
                userList = orderService.create(userList);
                LOGGER.debug("userList: {}", userList);
                payload.withMessage("Uploaded the file '%s' successfully!", file.getOriginalFilename());
                return ResponseEntity.status(HttpStatus.OK).body(payload);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not upload the file:{}!", file.getOriginalFilename(), ex);
            payload.withMessage("Could not upload the file '%s'!", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(payload);
        }
        
        payload.withMessage("Unsupported file type!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }
    
    
    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> download(@RequestParam("fileType") String fileType) {
        BeanUtils.assertNonNull(fileType, "Download 'fileType' must provide!");
        ResponseEntity responseEntity = null;
        InputStreamResource inputStreamResource = null;
        String contentDisposition;
        MediaType mediaType;
        OrderParser userParser = new OrderParser();
        try {
            if (CsvParser.isCSVFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(OrderParser.CSV_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(CsvParser.CSV_MEDIA_TYPE);
                inputStreamResource = userParser.buildCSVResourceStream(orderService.getAll());
            } else if (ExcelParser.isExcelFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(OrderParser.EXCEL_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(ExcelParser.EXCEL_MEDIA_TYPE);
                inputStreamResource = userParser.buildStreamResources(orderService.getAll());
            } else {
                throw new UnsupportedOperationException("Unsupported fileType:" + fileType);
            }
            
            // check inputStreamResource is not null
            if (Objects.nonNull(inputStreamResource)) {
                responseEntity = Parser.buildOKResponse(contentDisposition, mediaType, inputStreamResource);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return responseEntity;
    }
}
