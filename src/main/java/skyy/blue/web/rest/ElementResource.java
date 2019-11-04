package skyy.blue.web.rest;

import skyy.blue.domain.Element;
import skyy.blue.repository.ElementRepository;
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
 * REST controller for managing {@link skyy.blue.domain.Element}.
 */
@RestController
@RequestMapping("/api")
public class ElementResource {

    private final Logger log = LoggerFactory.getLogger(ElementResource.class);

    private static final String ENTITY_NAME = "element";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementRepository elementRepository;

    public ElementResource(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    /**
     * {@code POST  /elements} : Create a new element.
     *
     * @param element the element to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new element, or with status {@code 400 (Bad Request)} if the element has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elements")
    public ResponseEntity<Element> createElement(@RequestBody Element element) throws URISyntaxException {
        log.debug("REST request to save Element : {}", element);
        if (element.getId() != null) {
            throw new BadRequestAlertException("A new element cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Element result = elementRepository.save(element);
        return ResponseEntity.created(new URI("/api/elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elements} : Updates an existing element.
     *
     * @param element the element to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated element,
     * or with status {@code 400 (Bad Request)} if the element is not valid,
     * or with status {@code 500 (Internal Server Error)} if the element couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elements")
    public ResponseEntity<Element> updateElement(@RequestBody Element element) throws URISyntaxException {
        log.debug("REST request to update Element : {}", element);
        if (element.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Element result = elementRepository.save(element);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, element.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elements} : get all the elements.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elements in body.
     */
    @GetMapping("/elements")
    public ResponseEntity<List<Element>> getAllElements(Pageable pageable) {
        log.debug("REST request to get a page of Elements");
        Page<Element> page = elementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /elements/:id} : get the "id" element.
     *
     * @param id the id of the element to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the element, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elements/{id}")
    public ResponseEntity<Element> getElement(@PathVariable Long id) {
        log.debug("REST request to get Element : {}", id);
        Optional<Element> element = elementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(element);
    }

    /**
     * {@code DELETE  /elements/:id} : delete the "id" element.
     *
     * @param id the id of the element to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elements/{id}")
    public ResponseEntity<Void> deleteElement(@PathVariable Long id) {
        log.debug("REST request to delete Element : {}", id);
        elementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
