package org.jhipster.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityCitizenComplaintTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityCitizenComplaint.class);
        CityCitizenComplaint cityCitizenComplaint1 = new CityCitizenComplaint();
        cityCitizenComplaint1.setId(1L);
        CityCitizenComplaint cityCitizenComplaint2 = new CityCitizenComplaint();
        cityCitizenComplaint2.setId(cityCitizenComplaint1.getId());
        assertThat(cityCitizenComplaint1).isEqualTo(cityCitizenComplaint2);
        cityCitizenComplaint2.setId(2L);
        assertThat(cityCitizenComplaint1).isNotEqualTo(cityCitizenComplaint2);
        cityCitizenComplaint1.setId(null);
        assertThat(cityCitizenComplaint1).isNotEqualTo(cityCitizenComplaint2);
    }
}
