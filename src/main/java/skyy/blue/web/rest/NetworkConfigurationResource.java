package skyy.blue.web.rest;

import skyy.blue.domain.NetworkConfiguration;
import skyy.blue.repository.NetworkConfigurationRepository;
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
 * REST controller for managing {@link skyy.blue.domain.NetworkConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class NetworkConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(NetworkConfigurationResource.class);

    private static final String ENTITY_NAME = "networkConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetworkConfigurationRepository networkConfigurationRepository;

    public NetworkConfigurationResource(NetworkConfigurationRepository networkConfigurationRepository) {
        this.networkConfigurationRepository = networkConfigurationRepository;
    }

    /**
     * {@code POST  /network-configurations} : Create a new networkConfiguration.
     *
     * @param networkConfiguration the networkConfiguration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new networkConfiguration, or with status {@code 400 (Bad Request)} if the networkConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/network-configurations")
    public ResponseEntity<NetworkConfiguration> createNetworkConfiguration(@RequestBody NetworkConfiguration networkConfiguration) throws URISyntaxException {
        log.debug("REST request to save NetworkConfiguration : {}", networkConfiguration);
        if (networkConfiguration.getId() != null) {
            throw new BadRequestAlertException("A new networkConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetworkConfiguration result = networkConfigurationRepository.save(networkConfiguration);
        return ResponseEntity.created(new URI("/api/network-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /network-configurations} : Updates an existing networkConfiguration.
     *
     * @param networkConfiguration the networkConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networkConfiguration,
     * or with status {@code 400 (Bad Request)} if the networkConfiguration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the networkConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/network-configurations")
    public ResponseEntity<NetworkConfiguration> updateNetworkConfiguration(@RequestBody NetworkConfiguration networkConfiguration) throws URISyntaxException {
        log.debug("REST request to update NetworkConfiguration : {}", networkConfiguration);
        if (networkConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetworkConfiguration result = networkConfigurationRepository.save(networkConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networkConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /network-configurations} : get all the networkConfigurations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of networkConfigurations in body.
     */
    @GetMapping("/network-configurations")
    public ResponseEntity<List<NetworkConfiguration>> getAllNetworkConfigurations(Pageable pageable) {
        log.debug("REST request to get a page of NetworkConfigurations");
        Page<NetworkConfiguration> page = networkConfigurationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /network-configurations/:id} : get the "id" networkConfiguration.
     *
     * @param id the id of the networkConfiguration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the networkConfiguration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/network-configurations/{id}")
    public ResponseEntity<NetworkConfiguration> getNetworkConfiguration(@PathVariable Long id) {
        log.debug("REST request to get NetworkConfiguration : {}", id);
        Optional<NetworkConfiguration> networkConfiguration = networkConfigurationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(networkConfiguration);
    }

    /**
     * {@code DELETE  /network-configurations/:id} : delete the "id" networkConfiguration.
     *
     * @param id the id of the networkConfiguration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/network-configurations/{id}")
    public ResponseEntity<Void> deleteNetworkConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete NetworkConfiguration : {}", id);
        networkConfigurationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
