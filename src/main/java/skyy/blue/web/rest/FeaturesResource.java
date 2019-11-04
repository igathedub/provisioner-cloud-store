package skyy.blue.web.rest;

import skyy.blue.domain.Features;
import skyy.blue.repository.FeaturesRepository;
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
 * REST controller for managing {@link skyy.blue.domain.Features}.
 */
@RestController
@RequestMapping("/api")
public class FeaturesResource {

    private final Logger log = LoggerFactory.getLogger(FeaturesResource.class);

    private static final String ENTITY_NAME = "features";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeaturesRepository featuresRepository;

    public FeaturesResource(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    /**
     * {@code POST  /features} : Create a new features.
     *
     * @param features the features to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new features, or with status {@code 400 (Bad Request)} if the features has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<Features> createFeatures(@RequestBody Features features) throws URISyntaxException {
        log.debug("REST request to save Features : {}", features);
        if (features.getId() != null) {
            throw new BadRequestAlertException("A new features cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Features result = featuresRepository.save(features);
        return ResponseEntity.created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features} : Updates an existing features.
     *
     * @param features the features to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated features,
     * or with status {@code 400 (Bad Request)} if the features is not valid,
     * or with status {@code 500 (Internal Server Error)} if the features couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features")
    public ResponseEntity<Features> updateFeatures(@RequestBody Features features) throws URISyntaxException {
        log.debug("REST request to update Features : {}", features);
        if (features.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Features result = featuresRepository.save(features);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, features.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /features} : get all the features.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public ResponseEntity<List<Features>> getAllFeatures(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("node-is-null".equals(filter)) {
            log.debug("REST request to get all Featuress where node is null");
            return new ResponseEntity<>(StreamSupport
                .stream(featuresRepository.findAll().spliterator(), false)
                .filter(features -> features.getNode() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Features");
        Page<Features> page = featuresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /features/:id} : get the "id" features.
     *
     * @param id the id of the features to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the features, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<Features> getFeatures(@PathVariable Long id) {
        log.debug("REST request to get Features : {}", id);
        Optional<Features> features = featuresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(features);
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" features.
     *
     * @param id the id of the features to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeatures(@PathVariable Long id) {
        log.debug("REST request to delete Features : {}", id);
        featuresRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
