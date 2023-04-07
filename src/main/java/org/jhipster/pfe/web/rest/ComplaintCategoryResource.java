package org.jhipster.pfe.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.jhipster.pfe.domain.ComplaintCategory;
import org.jhipster.pfe.repository.ComplaintCategoryRepository;
import org.jhipster.pfe.repository.search.ComplaintCategorySearchRepository;
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
 * REST controller for managing {@link org.jhipster.pfe.domain.ComplaintCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComplaintCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintCategoryResource.class);

    private final ComplaintCategoryRepository complaintCategoryRepository;

    private final ComplaintCategorySearchRepository complaintCategorySearchRepository;

    public ComplaintCategoryResource(
        ComplaintCategoryRepository complaintCategoryRepository,
        ComplaintCategorySearchRepository complaintCategorySearchRepository
    ) {
        this.complaintCategoryRepository = complaintCategoryRepository;
        this.complaintCategorySearchRepository = complaintCategorySearchRepository;
    }

    /**
     * {@code GET  /complaint-categories} : get all the complaintCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complaintCategories in body.
     */
    @GetMapping("/complaint-categories")
    public ResponseEntity<List<ComplaintCategory>> getAllComplaintCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ComplaintCategories");
        Page<ComplaintCategory> page = complaintCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /complaint-categories/:id} : get the "id" complaintCategory.
     *
     * @param id the id of the complaintCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complaintCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/complaint-categories/{id}")
    public ResponseEntity<ComplaintCategory> getComplaintCategory(@PathVariable Long id) {
        log.debug("REST request to get ComplaintCategory : {}", id);
        Optional<ComplaintCategory> complaintCategory = complaintCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(complaintCategory);
    }

    /**
     * {@code SEARCH  /_search/complaint-categories?query=:query} : search for the complaintCategory corresponding
     * to the query.
     *
     * @param query the query of the complaintCategory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/complaint-categories")
    public ResponseEntity<List<ComplaintCategory>> searchComplaintCategories(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of ComplaintCategories for query {}", query);
        Page<ComplaintCategory> page = complaintCategorySearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
