package org.jhipster.pfe.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.pfe.domain.CityService;
import org.springframework.data.domain.Page;

public interface CityServiceRepositoryWithBagRelationships {
    Optional<CityService> fetchBagRelationships(Optional<CityService> cityService);

    List<CityService> fetchBagRelationships(List<CityService> cityServices);

    Page<CityService> fetchBagRelationships(Page<CityService> cityServices);
}
