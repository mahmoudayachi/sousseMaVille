package org.jhipster.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityService.class);
        CityService cityService1 = new CityService();
        cityService1.setId(1L);
        CityService cityService2 = new CityService();
        cityService2.setId(cityService1.getId());
        assertThat(cityService1).isEqualTo(cityService2);
        cityService2.setId(2L);
        assertThat(cityService1).isNotEqualTo(cityService2);
        cityService1.setId(null);
        assertThat(cityService1).isNotEqualTo(cityService2);
    }
}
