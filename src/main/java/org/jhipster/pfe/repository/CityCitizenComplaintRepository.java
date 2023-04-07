package org.jhipster.pfe.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CityCitizenComplaint entity.
 */
@Repository
public interface CityCitizenComplaintRepository extends JpaRepository<CityCitizenComplaint, Long> {
    @Query(
        "select cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint where cityCitizenComplaint.user.login = ?#{principal.username}"
    )
    List<CityCitizenComplaint> findByUserIsCurrentUser();

    default Optional<CityCitizenComplaint> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CityCitizenComplaint> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CityCitizenComplaint> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint left join fetch cityCitizenComplaint.complaintCategory left join fetch cityCitizenComplaint.user",
        countQuery = "select count(distinct cityCitizenComplaint) from CityCitizenComplaint cityCitizenComplaint"
    )
    Page<CityCitizenComplaint> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint left join fetch cityCitizenComplaint.complaintCategory left join fetch cityCitizenComplaint.user"
    )
    List<CityCitizenComplaint> findAllWithToOneRelationships();

    @Query(
        "select cityCitizenComplaint from CityCitizenComplaint cityCitizenComplaint left join fetch cityCitizenComplaint.complaintCategory left join fetch cityCitizenComplaint.user where cityCitizenComplaint.id =:id"
    )
    Optional<CityCitizenComplaint> findOneWithToOneRelationships(@Param("id") Long id);
}
