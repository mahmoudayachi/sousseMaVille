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
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.jhipster.pfe.repository.CityCitizenComplaintRepository;
import org.jhipster.pfe.repository.search.CityCitizenComplaintSearchRepository;
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
 * REST controller for managing {@link org.jhipster.pfe.domain.CityCitizenComplaint}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CityCitizenComplaintResource {

    private final Logger log = LoggerFactory.getLogger(CityCitizenComplaintResource.class);

    private static final String ENTITY_NAME = "cityCitizenComplaint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityCitizenComplaintRepository cityCitizenComplaintRepository;

    private final CityCitizenComplaintSearchRepository cityCitizenComplaintSearchRepository;

    public CityCitizenComplaintResource(
        CityCitizenComplaintRepository cityCitizenComplaintRepository,
        CityCitizenComplaintSearchRepository cityCitizenComplaintSearchRepository
    ) {
        this.cityCitizenComplaintRepository = cityCitizenComplaintRepository;
        this.cityCitizenComplaintSearchRepository = cityCitizenComplaintSearchRepository;
    }

    /**
     * {@code POST  /city-citizen-complaints} : Create a new cityCitizenComplaint.
     *
     * @param cityCitizenComplaint the cityCitizenComplaint to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityCitizenComplaint, or with status {@code 400 (Bad Request)} if the cityCitizenComplaint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-citizen-complaints")
    public ResponseEntity<CityCitizenComplaint> createCityCitizenComplaint(@Valid @RequestBody CityCitizenComplaint cityCitizenComplaint)
        throws URISyntaxException {
        log.debug("REST request to save CityCitizenComplaint : {}", cityCitizenComplaint);
        if (cityCitizenComplaint.getId() != null) {
            throw new BadRequestAlertException("A new cityCitizenComplaint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityCitizenComplaint result = cityCitizenComplaintRepository.save(cityCitizenComplaint);
        cityCitizenComplaintSearchRepository.index(result);
        return ResponseEntity
            .created(new URI("/api/city-citizen-complaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-citizen-complaints/:id} : Updates an existing cityCitizenComplaint.
     *
     * @param id the id of the cityCitizenComplaint to save.
     * @param cityCitizenComplaint the cityCitizenComplaint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityCitizenComplaint,
     * or with status {@code 400 (Bad Request)} if the cityCitizenComplaint is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityCitizenComplaint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-citizen-complaints/{id}")
    public ResponseEntity<CityCitizenComplaint> updateCityCitizenComplaint(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CityCitizenComplaint cityCitizenComplaint
    ) throws URISyntaxException {
        log.debug("REST request to update CityCitizenComplaint : {}, {}", id, cityCitizenComplaint);
        if (cityCitizenComplaint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityCitizenComplaint.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityCitizenComplaintRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityCitizenComplaint result = cityCitizenComplaintRepository.save(cityCitizenComplaint);
        cityCitizenComplaintSearchRepository.index(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityCitizenComplaint.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-citizen-complaints/:id} : Partial updates given fields of an existing cityCitizenComplaint, field will ignore if it is null
     *
     * @param id the id of the cityCitizenComplaint to save.
     * @param cityCitizenComplaint the cityCitizenComplaint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityCitizenComplaint,
     * or with status {@code 400 (Bad Request)} if the cityCitizenComplaint is not valid,
     * or with status {@code 404 (Not Found)} if the cityCitizenComplaint is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityCitizenComplaint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-citizen-complaints/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityCitizenComplaint> partialUpdateCityCitizenComplaint(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CityCitizenComplaint cityCitizenComplaint
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityCitizenComplaint partially : {}, {}", id, cityCitizenComplaint);
        if (cityCitizenComplaint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityCitizenComplaint.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityCitizenComplaintRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityCitizenComplaint> result = cityCitizenComplaintRepository
            .findById(cityCitizenComplaint.getId())
            .map(existingCityCitizenComplaint -> {
                if (cityCitizenComplaint.getAddress() != null) {
                    existingCityCitizenComplaint.setAddress(cityCitizenComplaint.getAddress());
                }
                if (cityCitizenComplaint.getDescription() != null) {
                    existingCityCitizenComplaint.setDescription(cityCitizenComplaint.getDescription());
                }
                if (cityCitizenComplaint.getDate() != null) {
                    existingCityCitizenComplaint.setDate(cityCitizenComplaint.getDate());
                }
                if (cityCitizenComplaint.getSharewithpublic() != null) {
                    existingCityCitizenComplaint.setSharewithpublic(cityCitizenComplaint.getSharewithpublic());
                }
                if (cityCitizenComplaint.getComplaintstate() != null) {
                    existingCityCitizenComplaint.setComplaintstate(cityCitizenComplaint.getComplaintstate());
                }
                if (cityCitizenComplaint.getFirstname() != null) {
                    existingCityCitizenComplaint.setFirstname(cityCitizenComplaint.getFirstname());
                }
                if (cityCitizenComplaint.getLastname() != null) {
                    existingCityCitizenComplaint.setLastname(cityCitizenComplaint.getLastname());
                }
                if (cityCitizenComplaint.getEmail() != null) {
                    existingCityCitizenComplaint.setEmail(cityCitizenComplaint.getEmail());
                }
                if (cityCitizenComplaint.getPhonenumber() != null) {
                    existingCityCitizenComplaint.setPhonenumber(cityCitizenComplaint.getPhonenumber());
                }
                if (cityCitizenComplaint.getGooglemapsx() != null) {
                    existingCityCitizenComplaint.setGooglemapsx(cityCitizenComplaint.getGooglemapsx());
                }
                if (cityCitizenComplaint.getGooglemapy() != null) {
                    existingCityCitizenComplaint.setGooglemapy(cityCitizenComplaint.getGooglemapy());
                }

                return existingCityCitizenComplaint;
            })
            .map(cityCitizenComplaintRepository::save)
            .map(savedCityCitizenComplaint -> {
                cityCitizenComplaintSearchRepository.save(savedCityCitizenComplaint);

                return savedCityCitizenComplaint;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityCitizenComplaint.getId().toString())
        );
    }

    /**
     * {@code GET  /city-citizen-complaints} : get all the cityCitizenComplaints.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityCitizenComplaints in body.
     */
    @GetMapping("/city-citizen-complaints")
    public ResponseEntity<List<CityCitizenComplaint>> getAllCityCitizenComplaints(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of CityCitizenComplaints");
        Page<CityCitizenComplaint> page;
        if (eagerload) {
            page = cityCitizenComplaintRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = cityCitizenComplaintRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-citizen-complaints/:id} : get the "id" cityCitizenComplaint.
     *
     * @param id the id of the cityCitizenComplaint to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityCitizenComplaint, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-citizen-complaints/{id}")
    public ResponseEntity<CityCitizenComplaint> getCityCitizenComplaint(@PathVariable Long id) {
        log.debug("REST request to get CityCitizenComplaint : {}", id);
        Optional<CityCitizenComplaint> cityCitizenComplaint = cityCitizenComplaintRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(cityCitizenComplaint);
    }

    /**
     * {@code DELETE  /city-citizen-complaints/:id} : delete the "id" cityCitizenComplaint.
     *
     * @param id the id of the cityCitizenComplaint to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-citizen-complaints/{id}")
    public ResponseEntity<Void> deleteCityCitizenComplaint(@PathVariable Long id) {
        log.debug("REST request to delete CityCitizenComplaint : {}", id);
        cityCitizenComplaintRepository.deleteById(id);
        cityCitizenComplaintSearchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/city-citizen-complaints?query=:query} : search for the cityCitizenComplaint corresponding
     * to the query.
     *
     * @param query the query of the cityCitizenComplaint search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/city-citizen-complaints")
    public ResponseEntity<List<CityCitizenComplaint>> searchCityCitizenComplaints(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of CityCitizenComplaints for query {}", query);
        Page<CityCitizenComplaint> page = cityCitizenComplaintSearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
