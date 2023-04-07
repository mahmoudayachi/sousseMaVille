package org.jhipster.pfe.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.jhipster.pfe.domain.CityCitizenPhoto;
import org.jhipster.pfe.repository.CityCitizenPhotoRepository;
import org.jhipster.pfe.repository.search.CityCitizenPhotoSearchRepository;
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
 * REST controller for managing {@link org.jhipster.pfe.domain.CityCitizenPhoto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CityCitizenPhotoResource {

    private final Logger log = LoggerFactory.getLogger(CityCitizenPhotoResource.class);

    private static final String ENTITY_NAME = "cityCitizenPhoto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityCitizenPhotoRepository cityCitizenPhotoRepository;

    private final CityCitizenPhotoSearchRepository cityCitizenPhotoSearchRepository;

    public CityCitizenPhotoResource(
        CityCitizenPhotoRepository cityCitizenPhotoRepository,
        CityCitizenPhotoSearchRepository cityCitizenPhotoSearchRepository
    ) {
        this.cityCitizenPhotoRepository = cityCitizenPhotoRepository;
        this.cityCitizenPhotoSearchRepository = cityCitizenPhotoSearchRepository;
    }

    /**
     * {@code POST  /city-citizen-photos} : Create a new cityCitizenPhoto.
     *
     * @param cityCitizenPhoto the cityCitizenPhoto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityCitizenPhoto, or with status {@code 400 (Bad Request)} if the cityCitizenPhoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-citizen-photos")
    public ResponseEntity<CityCitizenPhoto> createCityCitizenPhoto(@RequestBody CityCitizenPhoto cityCitizenPhoto)
        throws URISyntaxException {
        log.debug("REST request to save CityCitizenPhoto : {}", cityCitizenPhoto);
        if (cityCitizenPhoto.getId() != null) {
            throw new BadRequestAlertException("A new cityCitizenPhoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityCitizenPhoto result = cityCitizenPhotoRepository.save(cityCitizenPhoto);
        cityCitizenPhotoSearchRepository.index(result);
        return ResponseEntity
            .created(new URI("/api/city-citizen-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-citizen-photos/:id} : Updates an existing cityCitizenPhoto.
     *
     * @param id the id of the cityCitizenPhoto to save.
     * @param cityCitizenPhoto the cityCitizenPhoto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityCitizenPhoto,
     * or with status {@code 400 (Bad Request)} if the cityCitizenPhoto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityCitizenPhoto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-citizen-photos/{id}")
    public ResponseEntity<CityCitizenPhoto> updateCityCitizenPhoto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityCitizenPhoto cityCitizenPhoto
    ) throws URISyntaxException {
        log.debug("REST request to update CityCitizenPhoto : {}, {}", id, cityCitizenPhoto);
        if (cityCitizenPhoto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityCitizenPhoto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityCitizenPhotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityCitizenPhoto result = cityCitizenPhotoRepository.save(cityCitizenPhoto);
        cityCitizenPhotoSearchRepository.index(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityCitizenPhoto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-citizen-photos/:id} : Partial updates given fields of an existing cityCitizenPhoto, field will ignore if it is null
     *
     * @param id the id of the cityCitizenPhoto to save.
     * @param cityCitizenPhoto the cityCitizenPhoto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityCitizenPhoto,
     * or with status {@code 400 (Bad Request)} if the cityCitizenPhoto is not valid,
     * or with status {@code 404 (Not Found)} if the cityCitizenPhoto is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityCitizenPhoto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-citizen-photos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityCitizenPhoto> partialUpdateCityCitizenPhoto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityCitizenPhoto cityCitizenPhoto
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityCitizenPhoto partially : {}, {}", id, cityCitizenPhoto);
        if (cityCitizenPhoto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityCitizenPhoto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityCitizenPhotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityCitizenPhoto> result = cityCitizenPhotoRepository
            .findById(cityCitizenPhoto.getId())
            .map(existingCityCitizenPhoto -> {
                if (cityCitizenPhoto.getImage() != null) {
                    existingCityCitizenPhoto.setImage(cityCitizenPhoto.getImage());
                }
                if (cityCitizenPhoto.getImageContentType() != null) {
                    existingCityCitizenPhoto.setImageContentType(cityCitizenPhoto.getImageContentType());
                }

                return existingCityCitizenPhoto;
            })
            .map(cityCitizenPhotoRepository::save)
            .map(savedCityCitizenPhoto -> {
                cityCitizenPhotoSearchRepository.save(savedCityCitizenPhoto);

                return savedCityCitizenPhoto;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cityCitizenPhoto.getId().toString())
        );
    }

    /**
     * {@code GET  /city-citizen-photos} : get all the cityCitizenPhotos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityCitizenPhotos in body.
     */
    @GetMapping("/city-citizen-photos")
    public ResponseEntity<List<CityCitizenPhoto>> getAllCityCitizenPhotos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CityCitizenPhotos");
        Page<CityCitizenPhoto> page = cityCitizenPhotoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-citizen-photos/:id} : get the "id" cityCitizenPhoto.
     *
     * @param id the id of the cityCitizenPhoto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityCitizenPhoto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-citizen-photos/{id}")
    public ResponseEntity<CityCitizenPhoto> getCityCitizenPhoto(@PathVariable Long id) {
        log.debug("REST request to get CityCitizenPhoto : {}", id);
        Optional<CityCitizenPhoto> cityCitizenPhoto = cityCitizenPhotoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cityCitizenPhoto);
    }

    /**
     * {@code DELETE  /city-citizen-photos/:id} : delete the "id" cityCitizenPhoto.
     *
     * @param id the id of the cityCitizenPhoto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-citizen-photos/{id}")
    public ResponseEntity<Void> deleteCityCitizenPhoto(@PathVariable Long id) {
        log.debug("REST request to delete CityCitizenPhoto : {}", id);
        cityCitizenPhotoRepository.deleteById(id);
        cityCitizenPhotoSearchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/city-citizen-photos?query=:query} : search for the cityCitizenPhoto corresponding
     * to the query.
     *
     * @param query the query of the cityCitizenPhoto search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/city-citizen-photos")
    public ResponseEntity<List<CityCitizenPhoto>> searchCityCitizenPhotos(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of CityCitizenPhotos for query {}", query);
        Page<CityCitizenPhoto> page = cityCitizenPhotoSearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
