package com.rslakra.vantageservice.dsp.taxonomy.controller.rest;

import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.controller.rest.AbstractRestController;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.vantageservice.dsp.taxonomy.filter.ContentTaxonomyFilter;
import com.rslakra.vantageservice.dsp.taxonomy.persistence.entity.ContentTaxonomy;
import com.rslakra.vantageservice.dsp.taxonomy.service.ContentTaxonomyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author Rohtash Lakra
 * @created 5/25/22 5:08 PM
 */
@RestController
@RequestMapping("${restPrefix}/content-taxonomy")
public class ContentTaxonomyController extends AbstractRestController<ContentTaxonomy, Long> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentTaxonomyController.class);
    
    private final ContentTaxonomyService contentTaxonomyService;
    
    /**
     * @param contentTaxonomyService
     */
    @Autowired
    public ContentTaxonomyController(final ContentTaxonomyService contentTaxonomyService) {
        this.contentTaxonomyService = contentTaxonomyService;
    }
    
    /**
     * Returns the list of <code>T</code> objects.
     *
     * @return
     */
    @GetMapping
    @Override
    public List<ContentTaxonomy> getAll() {
        return contentTaxonomyService.getAll();
    }
    
    /**
     * Returns the list of <code>T</code> filters objects.
     *
     * @param allParams
     * @return
     */
    @GetMapping("/filter")
    @Override
    public List<ContentTaxonomy> getByFilter(@RequestParam Map<String, Object> allParams) {
        LOGGER.debug("+getByFilter({})", allParams);
        List<ContentTaxonomy> marketings = Collections.emptyList();
        ContentTaxonomyFilter contentTaxonomyFilter = new ContentTaxonomyFilter(allParams);
        if (contentTaxonomyFilter.hasKeys(ContentTaxonomyFilter.ID, ContentTaxonomyFilter.FIRST_NAME)) {
        } else if (contentTaxonomyFilter.hasKey(ContentTaxonomyFilter.ID)) {
            marketings = Arrays.asList(contentTaxonomyService.getById(
                    contentTaxonomyFilter.getValue(ContentTaxonomyFilter.ID, Long.class)));
        } else {
            marketings = contentTaxonomyService.getAll();
        }
        
        LOGGER.debug("-getByFilter(), marketings: {}", marketings);
        return marketings;
    }
    
    /**
     * Returns the <code>Page<T></code> list of objects filtered with <code>allParams</code>.
     *
     * @param allParams @return
     * @param pageable
     */
    @GetMapping("/pageable")
    @Override
    public Page<ContentTaxonomy> getByFilter(Map<String, Object> allParams, Pageable pageable) {
        return contentTaxonomyService.getByFilter(null, pageable);
    }
    
    /**
     * @param filter
     * @return
     */
    @Override
    public List<ContentTaxonomy> getByFilter(Filter filter) {
        return null;
    }
    
    /**
     * @param filter
     * @param pageable
     * @return
     */
    @Override
    public Page<ContentTaxonomy> getByFilter(Filter filter, Pageable pageable) {
        return null;
    }
    
    /**
     * Creates the <code>T</code> type object.
     *
     * @param task
     * @return
     */
    @PostMapping
    @ResponseBody
    @Override
    public ResponseEntity<ContentTaxonomy> create(@Validated @RequestBody ContentTaxonomy task) {
        task = contentTaxonomyService.create(task);
        return ResponseEntity.ok(task);
    }
    
    /**
     * Creates the list of <code>T</code> type objects.
     *
     * @param tasks
     * @return
     */
    @PostMapping("/batch")
    @ResponseBody
    @Override
    public ResponseEntity<List<ContentTaxonomy>> create(@Validated @RequestBody List<ContentTaxonomy> tasks) {
        tasks = contentTaxonomyService.create(tasks);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Updates the <code>T</code> type object.
     *
     * @param task
     * @return
     */
    @PutMapping
    @Override
    public ResponseEntity<ContentTaxonomy> update(@Validated @RequestBody ContentTaxonomy task) {
        task = contentTaxonomyService.update(task);
        return ResponseEntity.ok(task);
    }
    
    /**
     * Updates the list of <code>T</code> type objects.
     *
     * @param contentTaxonomies
     * @return
     */
    @PutMapping("/batch")
    @Override
    public ResponseEntity<List<ContentTaxonomy>> update(
            @Validated @RequestBody List<ContentTaxonomy> contentTaxonomies) {
        contentTaxonomies = contentTaxonomyService.update(contentTaxonomies);
        return ResponseEntity.ok(contentTaxonomies);
    }
    
    /**
     * Deletes the <code>T</code> type object by <code>id</code>.
     *
     * @param idOptional
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Payload> delete(@PathVariable(value = "id") Optional<Long> idOptional) {
        validate(idOptional);
        contentTaxonomyService.delete(idOptional.get());
        Payload payload = Payload.newBuilder()
                .withDeleted(Boolean.TRUE)
                .withMessage("Record with id:%d deleted successfully!", idOptional.get());
        return ResponseEntity.ok(payload);
    }
    
    /**
     * Uploads the <code>file</code>
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @Override
    public ResponseEntity<Payload> upload(MultipartFile file) {
        return null;
    }
    
    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> download(String fileType) {
        return null;
    }
    
}
