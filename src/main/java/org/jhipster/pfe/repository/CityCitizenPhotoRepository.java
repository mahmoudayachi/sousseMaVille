package org.jhipster.pfe.repository;

import org.jhipster.pfe.domain.CityCitizenPhoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CityCitizenPhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityCitizenPhotoRepository extends JpaRepository<CityCitizenPhoto, Long> {}
