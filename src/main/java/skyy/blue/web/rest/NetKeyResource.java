package skyy.blue.web.rest;

import skyy.blue.domain.NetKey;
import skyy.blue.repository.NetKeyRepository;
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
 * REST controller for managing {@link skyy.blue.domain.NetKey}.
 */
@RestController
@RequestMapping("/api")
public class NetKeyResource {

    private final Logger log = LoggerFactory.getLogger(NetKeyResource.class);

    private static final String ENTITY_NAME = "netKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetKeyRepository netKeyRepository;

    public NetKeyResource(NetKeyRepository netKeyRepository) {
        this.netKeyRepository = netKeyRepository;
    }

    /**
     * {@code POST  /net-keys} : Create a new netKey.
     *
     * @param netKey the netKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new netKey, or with status {@code 400 (Bad Request)} if the netKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/net-keys")
    public ResponseEntity<NetKey> createNetKey(@RequestBody NetKey netKey) throws URISyntaxException {
        log.debug("REST request to save NetKey : {}", netKey);
        if (netKey.getId() != null) {
            throw new BadRequestAlertException("A new netKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetKey result = netKeyRepository.save(netKey);
        return ResponseEntity.created(new URI("/api/net-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /net-keys} : Updates an existing netKey.
     *
     * @param netKey the netKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netKey,
     * or with status {@code 400 (Bad Request)} if the netKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the netKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/net-keys")
    public ResponseEntity<NetKey> updateNetKey(@RequestBody NetKey netKey) throws URISyntaxException {
        log.debug("REST request to update NetKey : {}", netKey);
        if (netKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetKey result = netKeyRepository.save(netKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /net-keys} : get all the netKeys.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of netKeys in body.
     */
    @GetMapping("/net-keys")
    public ResponseEntity<List<NetKey>> getAllNetKeys(Pageable pageable) {
        log.debug("REST request to get a page of NetKeys");
        Page<NetKey> page = netKeyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /net-keys/:id} : get the "id" netKey.
     *
     * @param id the id of the netKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the netKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/net-keys/{id}")
    public ResponseEntity<NetKey> getNetKey(@PathVariable Long id) {
        log.debug("REST request to get NetKey : {}", id);
        Optional<NetKey> netKey = netKeyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(netKey);
    }

    /**
     * {@code DELETE  /net-keys/:id} : delete the "id" netKey.
     *
     * @param id the id of the netKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/net-keys/{id}")
    public ResponseEntity<Void> deleteNetKey(@PathVariable Long id) {
        log.debug("REST request to delete NetKey : {}", id);
        netKeyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
