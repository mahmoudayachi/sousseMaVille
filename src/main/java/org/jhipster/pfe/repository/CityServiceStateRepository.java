package org.jhipster.pfe.repository;

import org.jhipster.pfe.domain.CityServiceState;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CityServiceState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityServiceStateRepository extends JpaRepository<CityServiceState, Long> {}
