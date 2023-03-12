package org.jhipster.pfe.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.pfe.domain.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CityService entity.
 *
 * When extending this class, extend CityServiceRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CityServiceRepository extends CityServiceRepositoryWithBagRelationships, JpaRepository<CityService, Long> {
    default Optional<CityService> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<CityService> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<CityService> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct cityService from CityService cityService left join fetch cityService.cityservicestate",
        countQuery = "select count(distinct cityService) from CityService cityService"
    )
    Page<CityService> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct cityService from CityService cityService left join fetch cityService.cityservicestate")
    List<CityService> findAllWithToOneRelationships();

    @Query("select cityService from CityService cityService left join fetch cityService.cityservicestate where cityService.id =:id")
    Optional<CityService> findOneWithToOneRelationships(@Param("id") Long id);
}
