package org.jhipster.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityServiceStateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityServiceState.class);
        CityServiceState cityServiceState1 = new CityServiceState();
        cityServiceState1.setId(1L);
        CityServiceState cityServiceState2 = new CityServiceState();
        cityServiceState2.setId(cityServiceState1.getId());
        assertThat(cityServiceState1).isEqualTo(cityServiceState2);
        cityServiceState2.setId(2L);
        assertThat(cityServiceState1).isNotEqualTo(cityServiceState2);
        cityServiceState1.setId(null);
        assertThat(cityServiceState1).isNotEqualTo(cityServiceState2);
    }
}
