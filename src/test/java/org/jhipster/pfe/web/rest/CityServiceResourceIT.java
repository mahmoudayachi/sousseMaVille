package org.jhipster.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.IterableUtil;
import org.jhipster.pfe.IntegrationTest;
import org.jhipster.pfe.domain.CityService;
import org.jhipster.pfe.domain.CityServiceState;
import org.jhipster.pfe.domain.UserRole;
import org.jhipster.pfe.repository.CityServiceRepository;
import org.jhipster.pfe.repository.search.CityServiceSearchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
 * Integration tests for the {@link CityServiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CityServiceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TOOLTIP = "AAAAAAAAAA";
    private static final String UPDATED_TOOLTIP = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/city-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/city-services";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityServiceRepository cityServiceRepository;

    @Mock
    private CityServiceRepository cityServiceRepositoryMock;

    @Autowired
    private CityServiceSearchRepository cityServiceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityServiceMockMvc;

    private CityService cityService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityService createEntity(EntityManager em) {
        CityService cityService = new CityService()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .tooltip(DEFAULT_TOOLTIP)
            .icon(DEFAULT_ICON)
            .order(DEFAULT_ORDER);
        // Add required entity
        CityServiceState cityServiceState;
        if (TestUtil.findAll(em, CityServiceState.class).isEmpty()) {
            cityServiceState = CityServiceStateResourceIT.createEntity(em);
            em.persist(cityServiceState);
            em.flush();
        } else {
            cityServiceState = TestUtil.findAll(em, CityServiceState.class).get(0);
        }
        cityService.setCityservicestate(cityServiceState);
        // Add required entity
        UserRole userRole;
        if (TestUtil.findAll(em, UserRole.class).isEmpty()) {
            userRole = UserRoleResourceIT.createEntity(em);
            em.persist(userRole);
            em.flush();
        } else {
            userRole = TestUtil.findAll(em, UserRole.class).get(0);
        }
        cityService.getUserroles().add(userRole);
        return cityService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityService createUpdatedEntity(EntityManager em) {
        CityService cityService = new CityService()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .tooltip(UPDATED_TOOLTIP)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER);
        // Add required entity
        CityServiceState cityServiceState;
        if (TestUtil.findAll(em, CityServiceState.class).isEmpty()) {
            cityServiceState = CityServiceStateResourceIT.createUpdatedEntity(em);
            em.persist(cityServiceState);
            em.flush();
        } else {
            cityServiceState = TestUtil.findAll(em, CityServiceState.class).get(0);
        }
        cityService.setCityservicestate(cityServiceState);
        // Add required entity
        UserRole userRole;
        if (TestUtil.findAll(em, UserRole.class).isEmpty()) {
            userRole = UserRoleResourceIT.createUpdatedEntity(em);
            em.persist(userRole);
            em.flush();
        } else {
            userRole = TestUtil.findAll(em, UserRole.class).get(0);
        }
        cityService.getUserroles().add(userRole);
        return cityService;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        cityServiceSearchRepository.deleteAll();
        assertThat(cityServiceSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        cityService = createEntity(em);
    }

    @Test
    @Transactional
    void createCityService() throws Exception {
        int databaseSizeBeforeCreate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // Create the CityService
        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isCreated());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        CityService testCityService = cityServiceList.get(cityServiceList.size() - 1);
        assertThat(testCityService.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCityService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCityService.getTooltip()).isEqualTo(DEFAULT_TOOLTIP);
        assertThat(testCityService.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testCityService.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    void createCityServiceWithExistingId() throws Exception {
        // Create the CityService with an existing ID
        cityService.setId(1L);

        int databaseSizeBeforeCreate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // set the field null
        cityService.setTitle(null);

        // Create the CityService, which fails.

        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // set the field null
        cityService.setDescription(null);

        // Create the CityService, which fails.

        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkTooltipIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // set the field null
        cityService.setTooltip(null);

        // Create the CityService, which fails.

        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // set the field null
        cityService.setIcon(null);

        // Create the CityService, which fails.

        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        // set the field null
        cityService.setOrder(null);

        // Create the CityService, which fails.

        restCityServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isBadRequest());

        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllCityServices() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);

        // Get all the cityServiceList
        restCityServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityService.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tooltip").value(hasItem(DEFAULT_TOOLTIP)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCityServicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(cityServiceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCityServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cityServiceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCityServicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cityServiceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCityServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cityServiceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCityService() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);

        // Get the cityService
        restCityServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, cityService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityService.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.tooltip").value(DEFAULT_TOOLTIP))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingCityService() throws Exception {
        // Get the cityService
        restCityServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityService() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);

        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        cityServiceSearchRepository.save(cityService);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());

        // Update the cityService
        CityService updatedCityService = cityServiceRepository.findById(cityService.getId()).get();
        // Disconnect from session so that the updates on updatedCityService are not directly saved in db
        em.detach(updatedCityService);
        updatedCityService
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .tooltip(UPDATED_TOOLTIP)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER);

        restCityServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCityService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCityService))
            )
            .andExpect(status().isOk());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        CityService testCityService = cityServiceList.get(cityServiceList.size() - 1);
        assertThat(testCityService.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCityService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityService.getTooltip()).isEqualTo(UPDATED_TOOLTIP);
        assertThat(testCityService.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCityService.getOrder()).isEqualTo(UPDATED_ORDER);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<CityService> cityServiceSearchList = IterableUtils.toList(cityServiceSearchRepository.findAll());
                CityService testCityServiceSearch = cityServiceSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCityServiceSearch.getTitle()).isEqualTo(UPDATED_TITLE);
                assertThat(testCityServiceSearch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
                assertThat(testCityServiceSearch.getTooltip()).isEqualTo(UPDATED_TOOLTIP);
                assertThat(testCityServiceSearch.getIcon()).isEqualTo(UPDATED_ICON);
                assertThat(testCityServiceSearch.getOrder()).isEqualTo(UPDATED_ORDER);
            });
    }

    @Test
    @Transactional
    void putNonExistingCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityService))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityService))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityService)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCityServiceWithPatch() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);

        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();

        // Update the cityService using partial update
        CityService partialUpdatedCityService = new CityService();
        partialUpdatedCityService.setId(cityService.getId());

        partialUpdatedCityService.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).tooltip(UPDATED_TOOLTIP).icon(UPDATED_ICON);

        restCityServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityService))
            )
            .andExpect(status().isOk());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        CityService testCityService = cityServiceList.get(cityServiceList.size() - 1);
        assertThat(testCityService.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCityService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityService.getTooltip()).isEqualTo(UPDATED_TOOLTIP);
        assertThat(testCityService.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCityService.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateCityServiceWithPatch() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);

        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();

        // Update the cityService using partial update
        CityService partialUpdatedCityService = new CityService();
        partialUpdatedCityService.setId(cityService.getId());

        partialUpdatedCityService
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .tooltip(UPDATED_TOOLTIP)
            .icon(UPDATED_ICON)
            .order(UPDATED_ORDER);

        restCityServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityService))
            )
            .andExpect(status().isOk());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        CityService testCityService = cityServiceList.get(cityServiceList.size() - 1);
        assertThat(testCityService.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCityService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityService.getTooltip()).isEqualTo(UPDATED_TOOLTIP);
        assertThat(testCityService.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCityService.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityService))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityService))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityService() throws Exception {
        int databaseSizeBeforeUpdate = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        cityService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cityService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityService in the database
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteCityService() throws Exception {
        // Initialize the database
        cityServiceRepository.saveAndFlush(cityService);
        cityServiceRepository.save(cityService);
        cityServiceSearchRepository.save(cityService);

        int databaseSizeBeforeDelete = cityServiceRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the cityService
        restCityServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityService> cityServiceList = cityServiceRepository.findAll();
        assertThat(cityServiceList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityServiceSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchCityService() throws Exception {
        // Initialize the database
        cityService = cityServiceRepository.saveAndFlush(cityService);
        cityServiceSearchRepository.save(cityService);

        // Search the cityService
        restCityServiceMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cityService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityService.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tooltip").value(hasItem(DEFAULT_TOOLTIP)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
}
