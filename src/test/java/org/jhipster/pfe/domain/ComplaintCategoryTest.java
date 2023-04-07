package org.jhipster.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplaintCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplaintCategory.class);
        ComplaintCategory complaintCategory1 = new ComplaintCategory();
        complaintCategory1.setId(1L);
        ComplaintCategory complaintCategory2 = new ComplaintCategory();
        complaintCategory2.setId(complaintCategory1.getId());
        assertThat(complaintCategory1).isEqualTo(complaintCategory2);
        complaintCategory2.setId(2L);
        assertThat(complaintCategory1).isNotEqualTo(complaintCategory2);
        complaintCategory1.setId(null);
        assertThat(complaintCategory1).isNotEqualTo(complaintCategory2);
    }
}
