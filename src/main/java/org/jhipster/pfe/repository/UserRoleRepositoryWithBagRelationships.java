package org.jhipster.pfe.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.pfe.domain.UserRole;
import org.springframework.data.domain.Page;

public interface UserRoleRepositoryWithBagRelationships {
    Optional<UserRole> fetchBagRelationships(Optional<UserRole> userRole);

    List<UserRole> fetchBagRelationships(List<UserRole> userRoles);

    Page<UserRole> fetchBagRelationships(Page<UserRole> userRoles);
}
