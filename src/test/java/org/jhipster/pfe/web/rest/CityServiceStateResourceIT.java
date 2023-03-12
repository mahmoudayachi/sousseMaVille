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
import org.jhipster.pfe.domain.CityServiceState;
import org.jhipster.pfe.domain.enumeration.state;
import org.jhipster.pfe.repository.CityServiceStateRepository;
import org.jhipster.pfe.repository.search.CityServiceStateSearchRepository;
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
 * Integration tests for the {@link CityServiceStateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityServiceStateResourceIT {

    private static final state DEFAULT_NAME = state.ACTIVATED;
    private static final state UPDATED_NAME = state.DESACTIVATED;

    private static final String ENTITY_API_URL = "/api/city-service-states";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/city-service-states";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityServiceStateRepository cityServiceStateRepository;

    @Autowired
    private CityServiceStateSearchRepository cityServiceStateSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityServiceStateMockMvc;

    private CityServiceState cityServiceState;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityServiceState createEntity(EntityManager em) {
        CityServiceState cityServiceState = new CityServiceState().name(DEFAULT_NAME);
        return cityServiceState;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityServiceState createUpdatedEntity(EntityManager em) {
        CityServiceState cityServiceState = new CityServiceState().name(UPDATED_NAME);
        return cityServiceState;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        cityServiceStateSearchRepository.deleteAll();
        assertThat(cityServiceStateSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        cityServiceState = createEntity(em);
    }

    @Test
    @Transactional
    void createCityServiceState() throws Exception {
        int databaseSizeBeforeCreate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        // Create the CityServiceState
        restCityServiceStateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isCreated());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        CityServiceState testCityServiceState = cityServiceStateList.get(cityServiceStateList.size() - 1);
        assertThat(testCityServiceState.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCityServiceStateWithExistingId() throws Exception {
        // Create the CityServiceState with an existing ID
        cityServiceState.setId(1L);

        int databaseSizeBeforeCreate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityServiceStateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        // set the field null
        cityServiceState.setName(null);

        // Create the CityServiceState, which fails.

        restCityServiceStateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllCityServiceStates() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);

        // Get all the cityServiceStateList
        restCityServiceStateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityServiceState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    void getCityServiceState() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);

        // Get the cityServiceState
        restCityServiceStateMockMvc
            .perform(get(ENTITY_API_URL_ID, cityServiceState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityServiceState.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCityServiceState() throws Exception {
        // Get the cityServiceState
        restCityServiceStateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityServiceState() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);

        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        cityServiceStateSearchRepository.save(cityServiceState);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());

        // Update the cityServiceState
        CityServiceState updatedCityServiceState = cityServiceStateRepository.findById(cityServiceState.getId()).get();
        // Disconnect from session so that the updates on updatedCityServiceState are not directly saved in db
        em.detach(updatedCityServiceState);
        updatedCityServiceState.name(UPDATED_NAME);

        restCityServiceStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCityServiceState.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCityServiceState))
            )
            .andExpect(status().isOk());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        CityServiceState testCityServiceState = cityServiceStateList.get(cityServiceStateList.size() - 1);
        assertThat(testCityServiceState.getName()).isEqualTo(UPDATED_NAME);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<CityServiceState> cityServiceStateSearchList = IterableUtils.toList(cityServiceStateSearchRepository.findAll());
                CityServiceState testCityServiceStateSearch = cityServiceStateSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCityServiceStateSearch.getName()).isEqualTo(UPDATED_NAME);
            });
    }

    @Test
    @Transactional
    void putNonExistingCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityServiceState.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCityServiceStateWithPatch() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);

        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();

        // Update the cityServiceState using partial update
        CityServiceState partialUpdatedCityServiceState = new CityServiceState();
        partialUpdatedCityServiceState.setId(cityServiceState.getId());

        restCityServiceStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityServiceState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityServiceState))
            )
            .andExpect(status().isOk());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        CityServiceState testCityServiceState = cityServiceStateList.get(cityServiceStateList.size() - 1);
        assertThat(testCityServiceState.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCityServiceStateWithPatch() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);

        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();

        // Update the cityServiceState using partial update
        CityServiceState partialUpdatedCityServiceState = new CityServiceState();
        partialUpdatedCityServiceState.setId(cityServiceState.getId());

        partialUpdatedCityServiceState.name(UPDATED_NAME);

        restCityServiceStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityServiceState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityServiceState))
            )
            .andExpect(status().isOk());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        CityServiceState testCityServiceState = cityServiceStateList.get(cityServiceStateList.size() - 1);
        assertThat(testCityServiceState.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityServiceState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityServiceState() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        cityServiceState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceStateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityServiceState))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityServiceState in the database
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteCityServiceState() throws Exception {
        // Initialize the database
        cityServiceStateRepository.saveAndFlush(cityServiceState);
        cityServiceStateRepository.save(cityServiceState);
        cityServiceStateSearchRepository.save(cityServiceState);

        int databaseSizeBeforeDelete = cityServiceStateRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the cityServiceState
        restCityServiceStateMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityServiceState.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityServiceState> cityServiceStateList = cityServiceStateRepository.findAll();
        assertThat(cityServiceStateList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceStateSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchCityServiceState() throws Exception {
        // Initialize the database
        cityServiceState = cityServiceStateRepository.saveAndFlush(cityServiceState);
        cityServiceStateSearchRepository.save(cityServiceState);

        // Search the cityServiceState
        restCityServiceStateMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cityServiceState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityServiceState.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
