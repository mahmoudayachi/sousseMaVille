package org.jhipster.pfe.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CityCitizenComplaintRepositoryWithBagRelationshipsImpl implements CityCitizenComplaintRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CityCitizenComplaint> fetchBagRelationships(Optional<CityCitizenComplaint> cityCitizenComplaint) {
        return cityCitizenComplaint.map(this::fetchCityCitizenPhotos);
    }

    @Override
    public Page<CityCitizenComplaint> fetchBagRelationships(Page<CityCitizenComplaint> cityCitizenComplaints) {
        return new PageImpl<>(
            fetchBagRelationships(cityCitizenComplaints.getContent()),
            cityCitizenComplaints.getPageable(),
            cityCitizenComplaints.getTotalElements()
        );
    }

    @Override
    public List<CityCitizenComplaint> fetchBagRelationships(List<CityCitizenComplaint> cityCitizenComplaints) {
        return Optional.of(cityCitizenComplaints).map(this::fetchCityCitizenPhotos).orElse(Collections.emptyList());
    }

    CityCitizenComplaint fetchCityCitizenPhotos(CityCitizenComplaint result) {
        return entityManager
            .createQuery(
                "select cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint left join fetch cityCitizenComplaint.cityCitizenPhotos where cityCitizenComplaint is :cityCitizenComplaint",
                CityCitizenComplaint.class
            )
            .setParameter("cityCitizenComplaint", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CityCitizenComplaint> fetchCityCitizenPhotos(List<CityCitizenComplaint> cityCitizenComplaints) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cityCitizenComplaints.size()).forEach(index -> order.put(cityCitizenComplaints.get(index).getId(), index));
        List<CityCitizenComplaint> result = entityManager
            .createQuery(
                "select distinct cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint left join fetch cityCitizenComplaint.cityCitizenPhotos where cityCitizenComplaint in :cityCitizenComplaints",
                CityCitizenComplaint.class
            )
            .setParameter("cityCitizenComplaints", cityCitizenComplaints)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
