package org.jhipster.pfe.repository;

import org.jhipster.pfe.domain.ComplaintCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ComplaintCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {}
