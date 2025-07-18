package com.rslakra.businessservice.advertising.controller;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.controller.web.AbstractWebController;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.parser.Parser;
import com.rslakra.appsuite.spring.parser.csv.CsvParser;
import com.rslakra.appsuite.spring.parser.excel.ExcelParser;
import com.rslakra.businessservice.advertising.parser.ContentTaxonomyParser;
import com.rslakra.businessservice.advertising.persistence.entity.ContentTaxonomy;
import com.rslakra.businessservice.advertising.service.ContentTaxonomyService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: Rohtash Lakra
 * @since 09/30/2019 05:38 PM
 */
@Controller
@RequestMapping("/content-taxonomy")
public class ContentTaxonomyWebController extends AbstractWebController<ContentTaxonomy, Long> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentTaxonomyWebController.class);
    
    private final ContentTaxonomyParser contentTaxonomyParser;
    
    // contentTaxonomyService
    private final ContentTaxonomyService contentTaxonomyService;
    
    /**
     * @param contentTaxonomyService
     */
    @Autowired
    public ContentTaxonomyWebController(ContentTaxonomyService contentTaxonomyService) {
        this.contentTaxonomyParser = new ContentTaxonomyParser();
        this.contentTaxonomyService = contentTaxonomyService;
    }
    
    /**
     * Saves the <code>t</code> object.
     *
     * @param contentTaxonomy
     * @return
     */
    @PostMapping("/save")
    @Override
    public String save(ContentTaxonomy contentTaxonomy) {
        if (BeanUtils.isNotNull(contentTaxonomy.getId())) {
            ContentTaxonomy oldContentTaxonomy = contentTaxonomyService.getById(contentTaxonomy.getId());
            BeanUtils.copyProperties(contentTaxonomy, oldContentTaxonomy);
            contentTaxonomy = contentTaxonomyService.update(oldContentTaxonomy);
        } else {
            contentTaxonomy = contentTaxonomyService.create(contentTaxonomy);
        }
        
        return "redirect:/content-taxonomy/list";
    }
    
    /**
     * Returns the list of <code>T</code> objects.
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    @Override
    public String getAll(Model model) {
        List<ContentTaxonomy> contentTaxonomies = contentTaxonomyService.getAll();
        model.addAttribute("contentTaxonomies", contentTaxonomies);
        return "views/advertising/content-taxonomy/listContentTaxonomies";
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
    public String filter(Model model, Filter filter) {
        List<ContentTaxonomy> contentTaxonomies = contentTaxonomyService.getAll();
        model.addAttribute("contentTaxonomies", contentTaxonomies);
        return "views/advertising/content-taxonomy/listContentTaxonomies";
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
    @RequestMapping(path = {"/create", "/update/{contentTaxonomyId}"})
    public String editObject(Model model, @PathVariable(name = "contentTaxonomyId") Optional<Long> idOptional) {
        ContentTaxonomy contentTaxonomy = null;
        if (idOptional.isPresent()) {
            contentTaxonomy = contentTaxonomyService.getById(idOptional.get());
        } else {
            contentTaxonomy = new ContentTaxonomy();
        }
        model.addAttribute("contentTaxonomy", contentTaxonomy);
        
        return "views/advertising/content-taxonomy/editContentTaxonomy";
    }
    
    /**
     * Deletes the object with <code>id</code>.
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/delete/{contentTaxonomyId}")
    @Override
    public String delete(Model model, @PathVariable(name = "contentTaxonomyId") Long id) {
        contentTaxonomyService.delete(id);
        return "redirect:/content-taxonomy/list";
    }
    
    /**
     * @return
     */
    @Override
    public Parser<ContentTaxonomy> getParser() {
        return contentTaxonomyParser;
    }
    
    /**
     * Displays the upload <code>ContentTaxonomys</code> UI.
     *
     * @return
     */
    @GetMapping(path = {"/upload"})
    public String showUploadPage() {
        return "views/advertising/content-taxonomy/uploadContentTaxonomies";
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
            List<ContentTaxonomy> contentTaxonomies = null;
            if (CsvParser.isCSVFile(file)) {
                contentTaxonomies = contentTaxonomyParser.readCSVStream(file.getInputStream());
            } else if (ExcelParser.isExcelFile(file)) {
                contentTaxonomies = contentTaxonomyParser.readStream(file.getInputStream());
            }
            
            // check the task list is available
            if (Objects.nonNull(contentTaxonomies)) {
                contentTaxonomies = contentTaxonomyService.create(contentTaxonomies);
                LOGGER.debug("contentTaxonomies: {}", contentTaxonomies);
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
        ResponseEntity responseEntity = null;
        InputStreamResource inputStreamResource = null;
        String contentDisposition;
        MediaType mediaType;
        try {
            if (CsvParser.isCSVFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(ContentTaxonomyParser.CSV_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(CsvParser.CSV_MEDIA_TYPE);
                inputStreamResource = contentTaxonomyParser.buildCSVResourceStream(contentTaxonomyService.getAll());
            } else if (ExcelParser.isExcelFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(ContentTaxonomyParser.EXCEL_DOWNLOAD_FILE_NAME);
                mediaType = Parser.getMediaType(ExcelParser.EXCEL_MEDIA_TYPE);
                inputStreamResource = contentTaxonomyParser.buildStreamResources(contentTaxonomyService.getAll());
            } else {
                throw new UnsupportedOperationException("Unsupported fileType:" + fileType);
            }
            
            // check inputStreamResource is not null
            if (Objects.nonNull(inputStreamResource)) {
                responseEntity = Parser.buildOKResponse(contentDisposition, mediaType, inputStreamResource);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        
        return responseEntity;
    }
}
