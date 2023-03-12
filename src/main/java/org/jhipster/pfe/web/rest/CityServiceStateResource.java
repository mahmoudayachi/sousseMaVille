package org.jhipster.pfe.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.jhipster.pfe.domain.CityServiceState;
import org.jhipster.pfe.repository.CityServiceStateRepository;
import org.jhipster.pfe.repository.search.CityServiceStateSearchRepository;
import org.jhipster.pfe.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.pfe.domain.CityServiceState}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CityServiceStateResource {

    private final Logger log = LoggerFactory.getLogger(CityServiceStateResource.class);

    private static final String ENTITY_NAME = "cityServiceState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityServiceStateRepository cityServiceStateRepository;

    private final CityServiceStateSearchRepository cityServiceStateSearchRepository;

    public CityServiceStateResource(
        CityServiceStateRepository cityServiceStateRepository,
        CityServiceStateSearchRepository cityServiceStateSearchRepository
    ) {
        this.cityServiceStateRepository = cityServiceStateRepository;
        this.cityServiceStateSearchRepository = cityServiceStateSearchRepository;
    }

    /**
     * {@code POST  /city-service-states} : Create a new cityServiceState.
     *
     * @param cityServiceState the cityServiceState to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityServiceState, or with status {@code 400 (Bad Request)} if the cityServiceState has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-service-states")
    public ResponseEntity<CityServiceState> createCityServiceState(@Valid @RequestBody CityServiceState cityServiceState)
        throws URISyntaxException {
        log.debug("REST request to save CityServiceState : {}", cityServiceState);
        if (cityServiceState.getId() != null) {
            throw new BadRequestAlertException("A new cityServiceState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityServiceState result = cityServiceStateRepository.save(cityServiceState);
        cityServiceStateSearchRepository.index(result);
        return ResponseEntity
            .created(new URI("/api/city-service-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-service-states/:id} : Updates an existing cityServiceState.
     *
     * @param id the id of the cityServiceState to save.
     * @param cityServiceState the cityServiceState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityServiceState,
     * or with status {@code 400 (Bad Request)} if the cityServiceState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityServiceState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-service-states/{id}")
    public ResponseEntity<CityServiceState> updateCityServiceState(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CityServiceState cityServiceState
    ) throws URISyntaxException {
        log.debug("REST request to update CityServiceState : {}, {}", id, cityServiceState);
        if (cityServiceState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityServiceState.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityServiceStateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityServiceState result = cityServiceStateRepository.save(cityServiceState);
        cityServiceStateSearchRepository.index(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityServiceState.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-service-states/:id} : Partial updates given fields of an existing cityServiceState, field will ignore if it is null
     *
     * @param id the id of the cityServiceState to save.
     * @param cityServiceState the cityServiceState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityServiceState,
     * or with status {@code 400 (Bad Request)} if the cityServiceState is not valid,
     * or with status {@code 404 (Not Found)} if the cityServiceState is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityServiceState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-service-states/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityServiceState> partialUpdateCityServiceState(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CityServiceState cityServiceState
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityServiceState partially : {}, {}", id, cityServiceState);
        if (cityServiceState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityServiceState.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityServiceStateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityServiceState> result = cityServiceStateRepository
            .findById(cityServiceState.getId())
            .map(existingCityServiceState -> {
                if (cityServiceState.getName() != null) {
                    existingCityServiceState.setName(cityServiceState.getName());
                }

                return existingCityServiceState;
            })
            .map(cityServiceStateRepository::save)
            .map(savedCityServiceState -> {
                cityServiceStateSearchRepository.save(savedCityServiceState);

                return savedCityServiceState;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityServiceState.getId().toString())
        );
    }

    /**
     * {@code GET  /city-service-states} : get all the cityServiceStates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityServiceStates in body.
     */
    @GetMapping("/city-service-states")
    public ResponseEntity<List<CityServiceState>> getAllCityServiceStates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CityServiceStates");
        Page<CityServiceState> page = cityServiceStateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-service-states/:id} : get the "id" cityServiceState.
     *
     * @param id the id of the cityServiceState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityServiceState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-service-states/{id}")
    public ResponseEntity<CityServiceState> getCityServiceState(@PathVariable Long id) {
        log.debug("REST request to get CityServiceState : {}", id);
        Optional<CityServiceState> cityServiceState = cityServiceStateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cityServiceState);
    }

    /**
     * {@code DELETE  /city-service-states/:id} : delete the "id" cityServiceState.
     *
     * @param id the id of the cityServiceState to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-service-states/{id}")
    public ResponseEntity<Void> deleteCityServiceState(@PathVariable Long id) {
        log.debug("REST request to delete CityServiceState : {}", id);
        cityServiceStateRepository.deleteById(id);
        cityServiceStateSearchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/city-service-states?query=:query} : search for the cityServiceState corresponding
     * to the query.
     *
     * @param query the query of the cityServiceState search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/city-service-states")
    public ResponseEntity<List<CityServiceState>> searchCityServiceStates(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of CityServiceStates for query {}", query);
        Page<CityServiceState> page = cityServiceStateSearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
