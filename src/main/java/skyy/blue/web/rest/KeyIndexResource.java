package skyy.blue.web.rest;

import skyy.blue.domain.KeyIndex;
import skyy.blue.repository.KeyIndexRepository;
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
 * REST controller for managing {@link skyy.blue.domain.KeyIndex}.
 */
@RestController
@RequestMapping("/api")
public class KeyIndexResource {

    private final Logger log = LoggerFactory.getLogger(KeyIndexResource.class);

    private static final String ENTITY_NAME = "keyIndex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyIndexRepository keyIndexRepository;

    public KeyIndexResource(KeyIndexRepository keyIndexRepository) {
        this.keyIndexRepository = keyIndexRepository;
    }

    /**
     * {@code POST  /key-indices} : Create a new keyIndex.
     *
     * @param keyIndex the keyIndex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyIndex, or with status {@code 400 (Bad Request)} if the keyIndex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-indices")
    public ResponseEntity<KeyIndex> createKeyIndex(@RequestBody KeyIndex keyIndex) throws URISyntaxException {
        log.debug("REST request to save KeyIndex : {}", keyIndex);
        if (keyIndex.getId() != null) {
            throw new BadRequestAlertException("A new keyIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyIndex result = keyIndexRepository.save(keyIndex);
        return ResponseEntity.created(new URI("/api/key-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-indices} : Updates an existing keyIndex.
     *
     * @param keyIndex the keyIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyIndex,
     * or with status {@code 400 (Bad Request)} if the keyIndex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-indices")
    public ResponseEntity<KeyIndex> updateKeyIndex(@RequestBody KeyIndex keyIndex) throws URISyntaxException {
        log.debug("REST request to update KeyIndex : {}", keyIndex);
        if (keyIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyIndex result = keyIndexRepository.save(keyIndex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyIndex.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /key-indices} : get all the keyIndices.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyIndices in body.
     */
    @GetMapping("/key-indices")
    public ResponseEntity<List<KeyIndex>> getAllKeyIndices(Pageable pageable) {
        log.debug("REST request to get a page of KeyIndices");
        Page<KeyIndex> page = keyIndexRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /key-indices/:id} : get the "id" keyIndex.
     *
     * @param id the id of the keyIndex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyIndex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-indices/{id}")
    public ResponseEntity<KeyIndex> getKeyIndex(@PathVariable Long id) {
        log.debug("REST request to get KeyIndex : {}", id);
        Optional<KeyIndex> keyIndex = keyIndexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(keyIndex);
    }

    /**
     * {@code DELETE  /key-indices/:id} : delete the "id" keyIndex.
     *
     * @param id the id of the keyIndex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-indices/{id}")
    public ResponseEntity<Void> deleteKeyIndex(@PathVariable Long id) {
        log.debug("REST request to delete KeyIndex : {}", id);
        keyIndexRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
