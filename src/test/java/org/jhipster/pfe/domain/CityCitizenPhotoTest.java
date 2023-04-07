package org.jhipster.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityCitizenPhotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityCitizenPhoto.class);
        CityCitizenPhoto cityCitizenPhoto1 = new CityCitizenPhoto();
        cityCitizenPhoto1.setId(1L);
        CityCitizenPhoto cityCitizenPhoto2 = new CityCitizenPhoto();
        cityCitizenPhoto2.setId(cityCitizenPhoto1.getId());
        assertThat(cityCitizenPhoto1).isEqualTo(cityCitizenPhoto2);
        cityCitizenPhoto2.setId(2L);
        assertThat(cityCitizenPhoto1).isNotEqualTo(cityCitizenPhoto2);
        cityCitizenPhoto1.setId(null);
        assertThat(cityCitizenPhoto1).isNotEqualTo(cityCitizenPhoto2);
    }
}
