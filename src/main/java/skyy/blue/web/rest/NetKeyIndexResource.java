package skyy.blue.web.rest;

import skyy.blue.domain.NetKeyIndex;
import skyy.blue.repository.NetKeyIndexRepository;
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
 * REST controller for managing {@link skyy.blue.domain.NetKeyIndex}.
 */
@RestController
@RequestMapping("/api")
public class NetKeyIndexResource {

    private final Logger log = LoggerFactory.getLogger(NetKeyIndexResource.class);

    private static final String ENTITY_NAME = "netKeyIndex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetKeyIndexRepository netKeyIndexRepository;

    public NetKeyIndexResource(NetKeyIndexRepository netKeyIndexRepository) {
        this.netKeyIndexRepository = netKeyIndexRepository;
    }

    /**
     * {@code POST  /net-key-indices} : Create a new netKeyIndex.
     *
     * @param netKeyIndex the netKeyIndex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new netKeyIndex, or with status {@code 400 (Bad Request)} if the netKeyIndex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/net-key-indices")
    public ResponseEntity<NetKeyIndex> createNetKeyIndex(@RequestBody NetKeyIndex netKeyIndex) throws URISyntaxException {
        log.debug("REST request to save NetKeyIndex : {}", netKeyIndex);
        if (netKeyIndex.getId() != null) {
            throw new BadRequestAlertException("A new netKeyIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetKeyIndex result = netKeyIndexRepository.save(netKeyIndex);
        return ResponseEntity.created(new URI("/api/net-key-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /net-key-indices} : Updates an existing netKeyIndex.
     *
     * @param netKeyIndex the netKeyIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netKeyIndex,
     * or with status {@code 400 (Bad Request)} if the netKeyIndex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the netKeyIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/net-key-indices")
    public ResponseEntity<NetKeyIndex> updateNetKeyIndex(@RequestBody NetKeyIndex netKeyIndex) throws URISyntaxException {
        log.debug("REST request to update NetKeyIndex : {}", netKeyIndex);
        if (netKeyIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetKeyIndex result = netKeyIndexRepository.save(netKeyIndex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netKeyIndex.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /net-key-indices} : get all the netKeyIndices.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of netKeyIndices in body.
     */
    @GetMapping("/net-key-indices")
    public ResponseEntity<List<NetKeyIndex>> getAllNetKeyIndices(Pageable pageable) {
        log.debug("REST request to get a page of NetKeyIndices");
        Page<NetKeyIndex> page = netKeyIndexRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /net-key-indices/:id} : get the "id" netKeyIndex.
     *
     * @param id the id of the netKeyIndex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the netKeyIndex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/net-key-indices/{id}")
    public ResponseEntity<NetKeyIndex> getNetKeyIndex(@PathVariable Long id) {
        log.debug("REST request to get NetKeyIndex : {}", id);
        Optional<NetKeyIndex> netKeyIndex = netKeyIndexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(netKeyIndex);
    }

    /**
     * {@code DELETE  /net-key-indices/:id} : delete the "id" netKeyIndex.
     *
     * @param id the id of the netKeyIndex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/net-key-indices/{id}")
    public ResponseEntity<Void> deleteNetKeyIndex(@PathVariable Long id) {
        log.debug("REST request to delete NetKeyIndex : {}", id);
        netKeyIndexRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
