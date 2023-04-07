package org.jhipster.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.IterableUtil;
import org.jhipster.pfe.IntegrationTest;
import org.jhipster.pfe.domain.ComplaintCategory;
import org.jhipster.pfe.repository.ComplaintCategoryRepository;
import org.jhipster.pfe.repository.search.ComplaintCategorySearchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComplaintCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplaintCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/complaint-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/complaint-categories";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComplaintCategoryRepository complaintCategoryRepository;

    @Autowired
    private ComplaintCategorySearchRepository complaintCategorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplaintCategoryMockMvc;

    private ComplaintCategory complaintCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplaintCategory createEntity(EntityManager em) {
        ComplaintCategory complaintCategory = new ComplaintCategory().name(DEFAULT_NAME).icon(DEFAULT_ICON);
        return complaintCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplaintCategory createUpdatedEntity(EntityManager em) {
        ComplaintCategory complaintCategory = new ComplaintCategory().name(UPDATED_NAME).icon(UPDATED_ICON);
        return complaintCategory;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        complaintCategorySearchRepository.deleteAll();
        assertThat(complaintCategorySearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        complaintCategory = createEntity(em);
    }

    @Test
    @Transactional
    void getAllComplaintCategories() throws Exception {
        // Initialize the database
        complaintCategoryRepository.saveAndFlush(complaintCategory);

        // Get all the complaintCategoryList
        restComplaintCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complaintCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    void getComplaintCategory() throws Exception {
        // Initialize the database
        complaintCategoryRepository.saveAndFlush(complaintCategory);

        // Get the complaintCategory
        restComplaintCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, complaintCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complaintCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }

    @Test
    @Transactional
    void getNonExistingComplaintCategory() throws Exception {
        // Get the complaintCategory
        restComplaintCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void searchComplaintCategory() throws Exception {
        // Initialize the database
        complaintCategory = complaintCategoryRepository.saveAndFlush(complaintCategory);
        complaintCategorySearchRepository.save(complaintCategory);

        // Search the complaintCategory
        restComplaintCategoryMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + complaintCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complaintCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
}
