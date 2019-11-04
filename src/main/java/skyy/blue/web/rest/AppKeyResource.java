package skyy.blue.web.rest;

import skyy.blue.domain.AppKey;
import skyy.blue.repository.AppKeyRepository;
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

/**
 * REST controller for managing {@link skyy.blue.domain.AppKey}.
 */
@RestController
@RequestMapping("/api")
public class AppKeyResource {

    private final Logger log = LoggerFactory.getLogger(AppKeyResource.class);

    private static final String ENTITY_NAME = "appKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppKeyRepository appKeyRepository;

    public AppKeyResource(AppKeyRepository appKeyRepository) {
        this.appKeyRepository = appKeyRepository;
    }

    /**
     * {@code POST  /app-keys} : Create a new appKey.
     *
     * @param appKey the appKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appKey, or with status {@code 400 (Bad Request)} if the appKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-keys")
    public ResponseEntity<AppKey> createAppKey(@RequestBody AppKey appKey) throws URISyntaxException {
        log.debug("REST request to save AppKey : {}", appKey);
        if (appKey.getId() != null) {
            throw new BadRequestAlertException("A new appKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppKey result = appKeyRepository.save(appKey);
        return ResponseEntity.created(new URI("/api/app-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-keys} : Updates an existing appKey.
     *
     * @param appKey the appKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appKey,
     * or with status {@code 400 (Bad Request)} if the appKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-keys")
    public ResponseEntity<AppKey> updateAppKey(@RequestBody AppKey appKey) throws URISyntaxException {
        log.debug("REST request to update AppKey : {}", appKey);
        if (appKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppKey result = appKeyRepository.save(appKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-keys} : get all the appKeys.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appKeys in body.
     */
    @GetMapping("/app-keys")
    public ResponseEntity<List<AppKey>> getAllAppKeys(Pageable pageable) {
        log.debug("REST request to get a page of AppKeys");
        Page<AppKey> page = appKeyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-keys/:id} : get the "id" appKey.
     *
     * @param id the id of the appKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-keys/{id}")
    public ResponseEntity<AppKey> getAppKey(@PathVariable Long id) {
        log.debug("REST request to get AppKey : {}", id);
        Optional<AppKey> appKey = appKeyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appKey);
    }

    /**
     * {@code DELETE  /app-keys/:id} : delete the "id" appKey.
     *
     * @param id the id of the appKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-keys/{id}")
    public ResponseEntity<Void> deleteAppKey(@PathVariable Long id) {
        log.debug("REST request to delete AppKey : {}", id);
        appKeyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
