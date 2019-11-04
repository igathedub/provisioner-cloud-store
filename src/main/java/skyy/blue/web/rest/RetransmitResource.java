package skyy.blue.web.rest;

import skyy.blue.domain.Retransmit;
import skyy.blue.repository.RetransmitRepository;
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
 * REST controller for managing {@link skyy.blue.domain.Retransmit}.
 */
@RestController
@RequestMapping("/api")
public class RetransmitResource {

    private final Logger log = LoggerFactory.getLogger(RetransmitResource.class);

    private static final String ENTITY_NAME = "retransmit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetransmitRepository retransmitRepository;

    public RetransmitResource(RetransmitRepository retransmitRepository) {
        this.retransmitRepository = retransmitRepository;
    }

    /**
     * {@code POST  /retransmits} : Create a new retransmit.
     *
     * @param retransmit the retransmit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new retransmit, or with status {@code 400 (Bad Request)} if the retransmit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/retransmits")
    public ResponseEntity<Retransmit> createRetransmit(@RequestBody Retransmit retransmit) throws URISyntaxException {
        log.debug("REST request to save Retransmit : {}", retransmit);
        if (retransmit.getId() != null) {
            throw new BadRequestAlertException("A new retransmit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Retransmit result = retransmitRepository.save(retransmit);
        return ResponseEntity.created(new URI("/api/retransmits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /retransmits} : Updates an existing retransmit.
     *
     * @param retransmit the retransmit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated retransmit,
     * or with status {@code 400 (Bad Request)} if the retransmit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the retransmit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/retransmits")
    public ResponseEntity<Retransmit> updateRetransmit(@RequestBody Retransmit retransmit) throws URISyntaxException {
        log.debug("REST request to update Retransmit : {}", retransmit);
        if (retransmit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Retransmit result = retransmitRepository.save(retransmit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, retransmit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /retransmits} : get all the retransmits.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of retransmits in body.
     */
    @GetMapping("/retransmits")
    public ResponseEntity<List<Retransmit>> getAllRetransmits(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("publish-is-null".equals(filter)) {
            log.debug("REST request to get all Retransmits where publish is null");
            return new ResponseEntity<>(StreamSupport
                .stream(retransmitRepository.findAll().spliterator(), false)
                .filter(retransmit -> retransmit.getPublish() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Retransmits");
        Page<Retransmit> page = retransmitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /retransmits/:id} : get the "id" retransmit.
     *
     * @param id the id of the retransmit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the retransmit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/retransmits/{id}")
    public ResponseEntity<Retransmit> getRetransmit(@PathVariable Long id) {
        log.debug("REST request to get Retransmit : {}", id);
        Optional<Retransmit> retransmit = retransmitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(retransmit);
    }

    /**
     * {@code DELETE  /retransmits/:id} : delete the "id" retransmit.
     *
     * @param id the id of the retransmit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/retransmits/{id}")
    public ResponseEntity<Void> deleteRetransmit(@PathVariable Long id) {
        log.debug("REST request to delete Retransmit : {}", id);
        retransmitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
