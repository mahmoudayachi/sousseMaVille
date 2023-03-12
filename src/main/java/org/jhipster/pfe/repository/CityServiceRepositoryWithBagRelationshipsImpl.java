package org.jhipster.pfe.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.jhipster.pfe.domain.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CityServiceRepositoryWithBagRelationshipsImpl implements CityServiceRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CityService> fetchBagRelationships(Optional<CityService> cityService) {
        return cityService.map(this::fetchUserroles);
    }

    @Override
    public Page<CityService> fetchBagRelationships(Page<CityService> cityServices) {
        return new PageImpl<>(
            fetchBagRelationships(cityServices.getContent()),
            cityServices.getPageable(),
            cityServices.getTotalElements()
        );
    }

    @Override
    public List<CityService> fetchBagRelationships(List<CityService> cityServices) {
        return Optional.of(cityServices).map(this::fetchUserroles).orElse(Collections.emptyList());
    }

    CityService fetchUserroles(CityService result) {
        return entityManager
            .createQuery(
                "select cityService from CityService cityService left join fetch cityService.userroles where cityService is :cityService",
                CityService.class
            )
            .setParameter("cityService", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CityService> fetchUserroles(List<CityService> cityServices) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cityServices.size()).forEach(index -> order.put(cityServices.get(index).getId(), index));
        List<CityService> result = entityManager
            .createQuery(
                "select distinct cityService from CityService cityService left join fetch cityService.userroles where cityService in :cityServices",
                CityService.class
            )
            .setParameter("cityServices", cityServices)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
