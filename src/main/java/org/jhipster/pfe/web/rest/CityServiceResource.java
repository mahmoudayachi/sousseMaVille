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
import org.jhipster.pfe.domain.CityService;
import org.jhipster.pfe.repository.CityServiceRepository;
import org.jhipster.pfe.repository.search.CityServiceSearchRepository;
import org.jhipster.pfe.service.CityServiceQueryService;
import org.jhipster.pfe.service.criteria.CityServiceCriteria;
import org.jhipster.pfe.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.pfe.domain.CityService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CityServiceResource {

    private final Logger log = LoggerFactory.getLogger(CityServiceResource.class);

    private static final String ENTITY_NAME = "cityService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityServiceRepository cityServiceRepository;

    private final CityServiceSearchRepository cityServiceSearchRepository;
    private final CityServiceQueryService cityServiceQueryService;

    public CityServiceResource(CityServiceRepository cityServiceRepository, CityServiceSearchRepository cityServiceSearchRepository) {
        this.cityServiceRepository = cityServiceRepository;
        this.cityServiceSearchRepository = cityServiceSearchRepository;
        this.cityServiceQueryService = new CityServiceQueryService(cityServiceRepository, cityServiceSearchRepository);
    }

    /**
     * {@code POST  /city-services} : Create a new cityService.
     *
     * @param cityService the cityService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityService, or with status {@code 400 (Bad Request)} if the cityService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-services")
    public ResponseEntity<CityService> createCityService(@Valid @RequestBody CityService cityService) throws URISyntaxException {
        log.debug("REST request to save CityService : {}", cityService);
        if (cityService.getId() != null) {
            throw new BadRequestAlertException("A new cityService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityService result = cityServiceRepository.save(cityService);
        cityServiceSearchRepository.index(result);
        return ResponseEntity
            .created(new URI("/api/city-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-services/:id} : Updates an existing cityService.
     *
     * @param id the id of the cityService to save.
     * @param cityService the cityService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityService,
     * or with status {@code 400 (Bad Request)} if the cityService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-services/{id}")
    public ResponseEntity<CityService> updateCityService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CityService cityService
    ) throws URISyntaxException {
        log.debug("REST request to update CityService : {}, {}", id, cityService);
        if (cityService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityService result = cityServiceRepository.save(cityService);
        cityServiceSearchRepository.index(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-services/:id} : Partial updates given fields of an existing cityService, field will ignore if it is null
     *
     * @param id the id of the cityService to save.
     * @param cityService the cityService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityService,
     * or with status {@code 400 (Bad Request)} if the cityService is not valid,
     * or with status {@code 404 (Not Found)} if the cityService is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityService> partialUpdateCityService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CityService cityService
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityService partially : {}, {}", id, cityService);
        if (cityService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityService> result = cityServiceRepository
            .findById(cityService.getId())
            .map(existingCityService -> {
                if (cityService.getTitle() != null) {
                    existingCityService.setTitle(cityService.getTitle());
                }
                if (cityService.getDescription() != null) {
                    existingCityService.setDescription(cityService.getDescription());
                }
                if (cityService.getTooltip() != null) {
                    existingCityService.setTooltip(cityService.getTooltip());
                }
                if (cityService.getIcon() != null) {
                    existingCityService.setIcon(cityService.getIcon());
                }
                if (cityService.getOrder() != null) {
                    existingCityService.setOrder(cityService.getOrder());
                }

                return existingCityService;
            })
            .map(cityServiceRepository::save)
            .map(savedCityService -> {
                cityServiceSearchRepository.save(savedCityService);

                return savedCityService;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityService.getId().toString())
        );
    }

    /**
     * {@code GET  /city-services} : get all the cityServices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityServices in body.
     */
    @GetMapping("/city-services")
    public ResponseEntity<List<CityService>> getAllCityServices(
        CityServiceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CityServices by criteria: {}", criteria);
        Page<CityService> page = cityServiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-services/count} : count all the cityServices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/city-services/count")
    public ResponseEntity<Long> countCityServices(CityServiceCriteria criteria) {
        log.debug("REST request to count CityServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(cityServiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /city-services/:id} : get the "id" cityService.
     *
     * @param id the id of the cityService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-services/{id}")
    public ResponseEntity<CityService> getCityService(@PathVariable Long id) {
        log.debug("REST request to get CityService : {}", id);
        Optional<CityService> cityService = cityServiceRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(cityService);
    }

    /**
     * {@code DELETE  /city-services/:id} : delete the "id" cityService.
     *
     * @param id the id of the cityService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-services/{id}")
    public ResponseEntity<Void> deleteCityService(@PathVariable Long id) {
        log.debug("REST request to delete CityService : {}", id);
        cityServiceRepository.deleteById(id);
        cityServiceSearchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/city-services?query=:query} : search for the cityService corresponding
     * to the query.
     *
     * @param query the query of the cityService search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/city-services")
    public ResponseEntity<List<CityService>> searchCityServices(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of CityServices for query {}", query);
        Page<CityService> page = cityServiceSearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
