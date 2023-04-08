package org.jhipster.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.jhipster.pfe.domain.CityCitizenPhoto;
import org.jhipster.pfe.domain.ComplaintCategory;
import org.jhipster.pfe.domain.User;
import org.jhipster.pfe.domain.enumeration.Complaintstate;
import org.jhipster.pfe.repository.CityCitizenComplaintRepository;
import org.jhipster.pfe.repository.search.CityCitizenComplaintSearchRepository;
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
 * Integration tests for the {@link CityCitizenComplaintResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CityCitizenComplaintResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SHAREWITHPUBLIC = false;
    private static final Boolean UPDATED_SHAREWITHPUBLIC = true;

    private static final Complaintstate DEFAULT_COMPLAINTSTATE = Complaintstate.RECEIVED;
    private static final Complaintstate UPDATED_COMPLAINTSTATE = Complaintstate.OPENED;

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/city-citizen-complaints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/city-citizen-complaints";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityCitizenComplaintRepository cityCitizenComplaintRepository;

    @Mock
    private CityCitizenComplaintRepository cityCitizenComplaintRepositoryMock;

    @Autowired
    private CityCitizenComplaintSearchRepository cityCitizenComplaintSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityCitizenComplaintMockMvc;

    private CityCitizenComplaint cityCitizenComplaint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityCitizenComplaint createEntity(EntityManager em) {
        CityCitizenComplaint cityCitizenComplaint = new CityCitizenComplaint()
            .address(DEFAULT_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .sharewithpublic(DEFAULT_SHAREWITHPUBLIC)
            .complaintstate(DEFAULT_COMPLAINTSTATE)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .phonenumber(DEFAULT_PHONENUMBER);
        // Add required entity
        ComplaintCategory complaintCategory;
        if (TestUtil.findAll(em, ComplaintCategory.class).isEmpty()) {
            complaintCategory = ComplaintCategoryResourceIT.createEntity(em);
            em.persist(complaintCategory);
            em.flush();
        } else {
            complaintCategory = TestUtil.findAll(em, ComplaintCategory.class).get(0);
        }
        cityCitizenComplaint.setComplaintCategory(complaintCategory);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cityCitizenComplaint.setUser(user);
        // Add required entity
        CityCitizenPhoto cityCitizenPhoto;
        if (TestUtil.findAll(em, CityCitizenPhoto.class).isEmpty()) {
            cityCitizenPhoto = CityCitizenPhotoResourceIT.createEntity(em);
            em.persist(cityCitizenPhoto);
            em.flush();
        } else {
            cityCitizenPhoto = TestUtil.findAll(em, CityCitizenPhoto.class).get(0);
        }
        cityCitizenComplaint.getCityCitizenPhotos().add(cityCitizenPhoto);
        return cityCitizenComplaint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityCitizenComplaint createUpdatedEntity(EntityManager em) {
        CityCitizenComplaint cityCitizenComplaint = new CityCitizenComplaint()
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .sharewithpublic(UPDATED_SHAREWITHPUBLIC)
            .complaintstate(UPDATED_COMPLAINTSTATE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phonenumber(UPDATED_PHONENUMBER);
        // Add required entity
        ComplaintCategory complaintCategory;
        if (TestUtil.findAll(em, ComplaintCategory.class).isEmpty()) {
            complaintCategory = ComplaintCategoryResourceIT.createUpdatedEntity(em);
            em.persist(complaintCategory);
            em.flush();
        } else {
            complaintCategory = TestUtil.findAll(em, ComplaintCategory.class).get(0);
        }
        cityCitizenComplaint.setComplaintCategory(complaintCategory);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cityCitizenComplaint.setUser(user);
        // Add required entity
        CityCitizenPhoto cityCitizenPhoto;
        if (TestUtil.findAll(em, CityCitizenPhoto.class).isEmpty()) {
            cityCitizenPhoto = CityCitizenPhotoResourceIT.createUpdatedEntity(em);
            em.persist(cityCitizenPhoto);
            em.flush();
        } else {
            cityCitizenPhoto = TestUtil.findAll(em, CityCitizenPhoto.class).get(0);
        }
        cityCitizenComplaint.getCityCitizenPhotos().add(cityCitizenPhoto);
        return cityCitizenComplaint;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        cityCitizenComplaintSearchRepository.deleteAll();
        assertThat(cityCitizenComplaintSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        cityCitizenComplaint = createEntity(em);
    }

    @Test
    @Transactional
    void createCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeCreate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        // Create the CityCitizenComplaint
        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isCreated());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        CityCitizenComplaint testCityCitizenComplaint = cityCitizenComplaintList.get(cityCitizenComplaintList.size() - 1);
        assertThat(testCityCitizenComplaint.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCityCitizenComplaint.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCityCitizenComplaint.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCityCitizenComplaint.getSharewithpublic()).isEqualTo(DEFAULT_SHAREWITHPUBLIC);
        assertThat(testCityCitizenComplaint.getComplaintstate()).isEqualTo(DEFAULT_COMPLAINTSTATE);
        assertThat(testCityCitizenComplaint.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCityCitizenComplaint.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testCityCitizenComplaint.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCityCitizenComplaint.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
    }

    @Test
    @Transactional
    void createCityCitizenComplaintWithExistingId() throws Exception {
        // Create the CityCitizenComplaint with an existing ID
        cityCitizenComplaint.setId(1L);

        int databaseSizeBeforeCreate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        // set the field null
        cityCitizenComplaint.setAddress(null);

        // Create the CityCitizenComplaint, which fails.

        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        // set the field null
        cityCitizenComplaint.setDescription(null);

        // Create the CityCitizenComplaint, which fails.

        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        // set the field null
        cityCitizenComplaint.setDate(null);

        // Create the CityCitizenComplaint, which fails.

        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkComplaintstateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        // set the field null
        cityCitizenComplaint.setComplaintstate(null);

        // Create the CityCitizenComplaint, which fails.

        restCityCitizenComplaintMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeTest);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllCityCitizenComplaints() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);

        // Get all the cityCitizenComplaintList
        restCityCitizenComplaintMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityCitizenComplaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sharewithpublic").value(hasItem(DEFAULT_SHAREWITHPUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].complaintstate").value(hasItem(DEFAULT_COMPLAINTSTATE.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCityCitizenComplaintsWithEagerRelationshipsIsEnabled() throws Exception {
        when(cityCitizenComplaintRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCityCitizenComplaintMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cityCitizenComplaintRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCityCitizenComplaintsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cityCitizenComplaintRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCityCitizenComplaintMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cityCitizenComplaintRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCityCitizenComplaint() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);

        // Get the cityCitizenComplaint
        restCityCitizenComplaintMockMvc
            .perform(get(ENTITY_API_URL_ID, cityCitizenComplaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityCitizenComplaint.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.sharewithpublic").value(DEFAULT_SHAREWITHPUBLIC.booleanValue()))
            .andExpect(jsonPath("$.complaintstate").value(DEFAULT_COMPLAINTSTATE.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER));
    }

    @Test
    @Transactional
    void getNonExistingCityCitizenComplaint() throws Exception {
        // Get the cityCitizenComplaint
        restCityCitizenComplaintMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityCitizenComplaint() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);

        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        cityCitizenComplaintSearchRepository.save(cityCitizenComplaint);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());

        // Update the cityCitizenComplaint
        CityCitizenComplaint updatedCityCitizenComplaint = cityCitizenComplaintRepository.findById(cityCitizenComplaint.getId()).get();
        // Disconnect from session so that the updates on updatedCityCitizenComplaint are not directly saved in db
        em.detach(updatedCityCitizenComplaint);
        updatedCityCitizenComplaint
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .sharewithpublic(UPDATED_SHAREWITHPUBLIC)
            .complaintstate(UPDATED_COMPLAINTSTATE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phonenumber(UPDATED_PHONENUMBER);

        restCityCitizenComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCityCitizenComplaint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCityCitizenComplaint))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenComplaint testCityCitizenComplaint = cityCitizenComplaintList.get(cityCitizenComplaintList.size() - 1);
        assertThat(testCityCitizenComplaint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCityCitizenComplaint.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityCitizenComplaint.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCityCitizenComplaint.getSharewithpublic()).isEqualTo(UPDATED_SHAREWITHPUBLIC);
        assertThat(testCityCitizenComplaint.getComplaintstate()).isEqualTo(UPDATED_COMPLAINTSTATE);
        assertThat(testCityCitizenComplaint.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCityCitizenComplaint.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCityCitizenComplaint.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCityCitizenComplaint.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<CityCitizenComplaint> cityCitizenComplaintSearchList = IterableUtils.toList(
                    cityCitizenComplaintSearchRepository.findAll()
                );
                CityCitizenComplaint testCityCitizenComplaintSearch = cityCitizenComplaintSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCityCitizenComplaintSearch.getAddress()).isEqualTo(UPDATED_ADDRESS);
                assertThat(testCityCitizenComplaintSearch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
                assertThat(testCityCitizenComplaintSearch.getDate()).isEqualTo(UPDATED_DATE);
                assertThat(testCityCitizenComplaintSearch.getSharewithpublic()).isEqualTo(UPDATED_SHAREWITHPUBLIC);
                assertThat(testCityCitizenComplaintSearch.getComplaintstate()).isEqualTo(UPDATED_COMPLAINTSTATE);
                assertThat(testCityCitizenComplaintSearch.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
                assertThat(testCityCitizenComplaintSearch.getLastname()).isEqualTo(UPDATED_LASTNAME);
                assertThat(testCityCitizenComplaintSearch.getEmail()).isEqualTo(UPDATED_EMAIL);
                assertThat(testCityCitizenComplaintSearch.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
            });
    }

    @Test
    @Transactional
    void putNonExistingCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityCitizenComplaint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCityCitizenComplaintWithPatch() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);

        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();

        // Update the cityCitizenComplaint using partial update
        CityCitizenComplaint partialUpdatedCityCitizenComplaint = new CityCitizenComplaint();
        partialUpdatedCityCitizenComplaint.setId(cityCitizenComplaint.getId());

        partialUpdatedCityCitizenComplaint
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .sharewithpublic(UPDATED_SHAREWITHPUBLIC)
            .complaintstate(UPDATED_COMPLAINTSTATE)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phonenumber(UPDATED_PHONENUMBER);

        restCityCitizenComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityCitizenComplaint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityCitizenComplaint))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenComplaint testCityCitizenComplaint = cityCitizenComplaintList.get(cityCitizenComplaintList.size() - 1);
        assertThat(testCityCitizenComplaint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCityCitizenComplaint.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityCitizenComplaint.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCityCitizenComplaint.getSharewithpublic()).isEqualTo(UPDATED_SHAREWITHPUBLIC);
        assertThat(testCityCitizenComplaint.getComplaintstate()).isEqualTo(UPDATED_COMPLAINTSTATE);
        assertThat(testCityCitizenComplaint.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCityCitizenComplaint.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCityCitizenComplaint.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCityCitizenComplaint.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void fullUpdateCityCitizenComplaintWithPatch() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);

        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();

        // Update the cityCitizenComplaint using partial update
        CityCitizenComplaint partialUpdatedCityCitizenComplaint = new CityCitizenComplaint();
        partialUpdatedCityCitizenComplaint.setId(cityCitizenComplaint.getId());

        partialUpdatedCityCitizenComplaint
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .sharewithpublic(UPDATED_SHAREWITHPUBLIC)
            .complaintstate(UPDATED_COMPLAINTSTATE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .phonenumber(UPDATED_PHONENUMBER);

        restCityCitizenComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityCitizenComplaint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityCitizenComplaint))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenComplaint testCityCitizenComplaint = cityCitizenComplaintList.get(cityCitizenComplaintList.size() - 1);
        assertThat(testCityCitizenComplaint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCityCitizenComplaint.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCityCitizenComplaint.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCityCitizenComplaint.getSharewithpublic()).isEqualTo(UPDATED_SHAREWITHPUBLIC);
        assertThat(testCityCitizenComplaint.getComplaintstate()).isEqualTo(UPDATED_COMPLAINTSTATE);
        assertThat(testCityCitizenComplaint.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCityCitizenComplaint.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCityCitizenComplaint.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCityCitizenComplaint.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityCitizenComplaint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityCitizenComplaint() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        cityCitizenComplaint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenComplaint))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityCitizenComplaint in the database
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteCityCitizenComplaint() throws Exception {
        // Initialize the database
        cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);
        cityCitizenComplaintRepository.save(cityCitizenComplaint);
        cityCitizenComplaintSearchRepository.save(cityCitizenComplaint);

        int databaseSizeBeforeDelete = cityCitizenComplaintRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the cityCitizenComplaint
        restCityCitizenComplaintMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityCitizenComplaint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityCitizenComplaint> cityCitizenComplaintList = cityCitizenComplaintRepository.findAll();
        assertThat(cityCitizenComplaintList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenComplaintSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchCityCitizenComplaint() throws Exception {
        // Initialize the database
        cityCitizenComplaint = cityCitizenComplaintRepository.saveAndFlush(cityCitizenComplaint);
        cityCitizenComplaintSearchRepository.save(cityCitizenComplaint);

        // Search the cityCitizenComplaint
        restCityCitizenComplaintMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cityCitizenComplaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityCitizenComplaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sharewithpublic").value(hasItem(DEFAULT_SHAREWITHPUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].complaintstate").value(hasItem(DEFAULT_COMPLAINTSTATE.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER)));
    }
}
