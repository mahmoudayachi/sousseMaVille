package org.jhipster.pfe.repository;

import java.util.List;
import java.util.Optional;
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.springframework.data.domain.Page;

public interface CityCitizenComplaintRepositoryWithBagRelationships {
    Optional<CityCitizenComplaint> fetchBagRelationships(Optional<CityCitizenComplaint> cityCitizenComplaint);

    List<CityCitizenComplaint> fetchBagRelationships(List<CityCitizenComplaint> cityCitizenComplaints);

    Page<CityCitizenComplaint> fetchBagRelationships(Page<CityCitizenComplaint> cityCitizenComplaints);
}
