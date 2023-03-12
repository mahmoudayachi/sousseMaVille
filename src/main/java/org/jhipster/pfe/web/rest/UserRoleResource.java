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
import org.jhipster.pfe.domain.UserRole;
import org.jhipster.pfe.repository.UserRoleRepository;
import org.jhipster.pfe.repository.search.UserRoleSearchRepository;
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
 * REST controller for managing {@link org.jhipster.pfe.domain.UserRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private final UserRoleRepository userRoleRepository;

    private final UserRoleSearchRepository userRoleSearchRepository;

    public UserRoleResource(UserRoleRepository userRoleRepository, UserRoleSearchRepository userRoleSearchRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleSearchRepository = userRoleSearchRepository;
    }

    /**
     * {@code GET  /user-roles} : get all the userRoles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRoles in body.
     */
    @GetMapping("/user-roles")
    public ResponseEntity<List<UserRole>> getAllUserRoles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserRoles");
        Page<UserRole> page;
        if (eagerload) {
            page = userRoleRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = userRoleRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-roles/:id} : get the "id" userRole.
     *
     * @param id the id of the userRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-roles/{id}")
    public ResponseEntity<UserRole> getUserRole(@PathVariable Long id) {
        log.debug("REST request to get UserRole : {}", id);
        Optional<UserRole> userRole = userRoleRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(userRole);
    }

    /**
     * {@code SEARCH  /_search/user-roles?query=:query} : search for the userRole corresponding
     * to the query.
     *
     * @param query the query of the userRole search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-roles")
    public ResponseEntity<List<UserRole>> searchUserRoles(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of UserRoles for query {}", query);
        Page<UserRole> page = userRoleSearchRepository.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
