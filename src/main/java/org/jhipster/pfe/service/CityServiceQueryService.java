package org.jhipster.pfe.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.jhipster.pfe.domain.*; // for static metamodels
import org.jhipster.pfe.domain.CityService;
import org.jhipster.pfe.repository.CityServiceRepository;
import org.jhipster.pfe.repository.search.CityServiceSearchRepository;
import org.jhipster.pfe.service.criteria.CityServiceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CityService} entities in the database.
 * The main input is a {@link CityServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CityService} or a {@link Page} of {@link CityService} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CityServiceQueryService extends QueryService<CityService> {

    private final Logger log = LoggerFactory.getLogger(CityServiceQueryService.class);

    private final CityServiceRepository cityServiceRepository;

    private final CityServiceSearchRepository cityServiceSearchRepository;

    public CityServiceQueryService(CityServiceRepository cityServiceRepository, CityServiceSearchRepository cityServiceSearchRepository) {
        this.cityServiceRepository = cityServiceRepository;
        this.cityServiceSearchRepository = cityServiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CityService} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CityService> findByCriteria(CityServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CityService> specification = createSpecification(criteria);
        return cityServiceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CityService} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CityService> findByCriteria(CityServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CityService> specification = createSpecification(criteria);
        return cityServiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CityServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CityService> specification = createSpecification(criteria);
        return cityServiceRepository.count(specification);
    }

    /**
     * Function to convert {@link CityServiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CityService> createSpecification(CityServiceCriteria criteria) {
        Specification<CityService> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CityService_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), CityService_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CityService_.description));
            }
            if (criteria.getTooltip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTooltip(), CityService_.tooltip));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), CityService_.icon));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), CityService_.order));
            }
            if (criteria.getCityservicestateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCityservicestateId(),
                            root -> root.join(CityService_.cityservicestate, JoinType.LEFT).get(CityServiceState_.id)
                        )
                    );
            }
            if (criteria.getUserroleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserroleId(),
                            root -> root.join(CityService_.userroles, JoinType.LEFT).get(UserRole_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
