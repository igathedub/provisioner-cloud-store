package skyy.blue.web.rest;

import skyy.blue.domain.Publish;
import skyy.blue.repository.PublishRepository;
import skyy.blue.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link skyy.blue.domain.Publish}.
 */
@RestController
@RequestMapping("/api")
public class PublishResource {

    private final Logger log = LoggerFactory.getLogger(PublishResource.class);

    private static final String ENTITY_NAME = "publish";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublishRepository publishRepository;

    public PublishResource(PublishRepository publishRepository) {
        this.publishRepository = publishRepository;
    }

    /**
     * {@code POST  /publishes} : Create a new publish.
     *
     * @param publish the publish to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publish, or with status {@code 400 (Bad Request)} if the publish has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publishes")
    public ResponseEntity<Publish> createPublish(@RequestBody Publish publish) throws URISyntaxException {
        log.debug("REST request to save Publish : {}", publish);
        if (publish.getId() != null) {
            throw new BadRequestAlertException("A new publish cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publish result = publishRepository.save(publish);
        return ResponseEntity.created(new URI("/api/publishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publishes} : Updates an existing publish.
     *
     * @param publish the publish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publish,
     * or with status {@code 400 (Bad Request)} if the publish is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publishes")
    public ResponseEntity<Publish> updatePublish(@RequestBody Publish publish) throws URISyntaxException {
        log.debug("REST request to update Publish : {}", publish);
        if (publish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Publish result = publishRepository.save(publish);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publish.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /publishes} : get all the publishes.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publishes in body.
     */
    @GetMapping("/publishes")
    public ResponseEntity<List<Publish>> getAllPublishes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("model-is-null".equals(filter)) {
            log.debug("REST request to get all Publishs where model is null");
            return new ResponseEntity<>(StreamSupport
                .stream(publishRepository.findAll().spliterator(), false)
                .filter(publish -> publish.getModel() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Publishes");
        Page<Publish> page = publishRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publishes/:id} : get the "id" publish.
     *
     * @param id the id of the publish to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publish, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publishes/{id}")
    public ResponseEntity<Publish> getPublish(@PathVariable Long id) {
        log.debug("REST request to get Publish : {}", id);
        Optional<Publish> publish = publishRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publish);
    }

    /**
     * {@code DELETE  /publishes/:id} : delete the "id" publish.
     *
     * @param id the id of the publish to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publishes/{id}")
    public ResponseEntity<Void> deletePublish(@PathVariable Long id) {
        log.debug("REST request to delete Publish : {}", id);
        publishRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
