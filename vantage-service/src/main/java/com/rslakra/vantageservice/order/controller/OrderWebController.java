package com.rslakra.vantageservice.order.controller;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.controller.web.AbstractWebController;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.parser.Parser;
import com.rslakra.appsuite.spring.parser.csv.CsvParser;
import com.rslakra.appsuite.spring.parser.excel.ExcelParser;
import com.rslakra.vantageservice.account.parser.UserParser;
import com.rslakra.vantageservice.order.parser.OrderParser;
import com.rslakra.vantageservice.order.persistence.entity.Order;
import com.rslakra.vantageservice.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@Controller
@RequestMapping("/orders")
public class OrderWebController extends AbstractWebController<Order, Long> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderWebController.class);
    
    private final OrderParser orderParser;
    private final OrderService orderService;
    
    /**
     * @param orderService
     */
    @Autowired
    public OrderWebController(OrderService orderService) {
        this.orderParser = new OrderParser();
        this.orderService = orderService;
    }
    
    /**
     * Saves the <code>t</code> object.
     *
     * @param order
     * @return
     */
    @PostMapping("/save")
    @Override
    public String save(Order order) {
        if (BeanUtils.isNotNull(order.getId())) {
            Order oldUser = orderService.getById(order.getId());
            BeanUtils.copyProperties(order, oldUser);
            order = orderService.update(oldUser);
        } else {
            order = orderService.create(order);
        }
        
        return "redirect:/orders/list";
    }
    
    /**
     * Returns the list of <code>T</code> objects.
     *
     * @param model
     * @return
     */
    @GetMapping(path = {"", "/", "/list"})
    @Override
    public String getAll(Model model) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "views/account/order/listOrders";
    }
    
    /**
     * Filters the list of <code>T</code> objects.
     *
     * @param model
     * @param filter
     * @return
     */
    @GetMapping("/filter")
    @Override
    public String filter(Model model, Filter<Order> filter) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "views/account/order/listOrders";
    }
    
    /**
     * @param model
     * @param allParams
     * @return
     */
    @Override
    public String filter(Model model, Map<String, Object> allParams) {
        return null;
    }
    
    /**
     * @param model
     * @param idOptional
     * @return
     */
    @RequestMapping(path = {"/create", "/update/{userId}"})
    @Override
    public String editObject(Model model, @PathVariable(name = "userId") Optional<Long> idOptional) {
        Order order = null;
        if (idOptional.isPresent()) {
            order = orderService.getById(idOptional.get());
        } else {
            order = new Order();
        }
        model.addAttribute("order", order);
        
        return "views/account/order/editUser";
    }
    
    /**
     * Deletes the object with <code>id</code>.
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/delete/{userId}")
    @Override
    public String delete(Model model, @PathVariable(name = "userId") Long id) {
        orderService.delete(id);
        return "redirect:/orders/list";
    }
    
    /**
     * @return
     */
    @Override
    public OrderParser getParser() {
        return orderParser;
    }
    
    /**
     * Displays the upload <code>Users</code> UI.
     *
     * @return
     */
    @GetMapping(path = {"/upload"})
    public String showUploadPage() {
        return "views/account/order/uploadUsers";
    }
    
    /**
     * Uploads the file of <code>Roles</code>.
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<Payload> upload(@RequestParam("file") MultipartFile file) {
        Payload payload = Payload.newBuilder();
        try {
            List<Order> orders = null;
            if (CsvParser.isCSVFile(file)) {
                orders = getParser().readCSVStream(file.getInputStream());
            } else if (ExcelParser.isExcelFile(file)) {
                orders = getParser().readStream(file.getInputStream());
            }
            
            // check the task list is available
            if (Objects.nonNull(orders)) {
                orders = orderService.create(orders);
                LOGGER.debug("orders: {}", orders);
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
     * @return
     */
    @Override
    public String showDownloadPage() {
        return null;
    }
    
    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("fileType") String fileType) {
        BeanUtils.assertNonNull(fileType, "Download 'fileType' must provide!");
        ResponseEntity<Resource> responseEntity = null;
        InputStreamResource inputStreamResource = null;
        String contentDisposition;
        MediaType mediaType;
        try {
            if (CsvParser.isCSVFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(UserParser.CSV_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(CsvParser.CSV_MEDIA_TYPE);
                inputStreamResource = getParser().buildCSVResourceStream(orderService.getAll());
            } else if (ExcelParser.isExcelFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(UserParser.EXCEL_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(ExcelParser.EXCEL_MEDIA_TYPE);
                inputStreamResource = getParser().buildStreamResources(orderService.getAll());
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
