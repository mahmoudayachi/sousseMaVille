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
import org.jhipster.pfe.domain.CityCitizenPhoto;
import org.jhipster.pfe.repository.CityCitizenPhotoRepository;
import org.jhipster.pfe.repository.search.CityCitizenPhotoSearchRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CityCitizenPhotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityCitizenPhotoResourceIT {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/city-citizen-photos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/city-citizen-photos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityCitizenPhotoRepository cityCitizenPhotoRepository;

    @Autowired
    private CityCitizenPhotoSearchRepository cityCitizenPhotoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityCitizenPhotoMockMvc;

    private CityCitizenPhoto cityCitizenPhoto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityCitizenPhoto createEntity(EntityManager em) {
        CityCitizenPhoto cityCitizenPhoto = new CityCitizenPhoto().image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return cityCitizenPhoto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityCitizenPhoto createUpdatedEntity(EntityManager em) {
        CityCitizenPhoto cityCitizenPhoto = new CityCitizenPhoto().image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return cityCitizenPhoto;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        cityCitizenPhotoSearchRepository.deleteAll();
        assertThat(cityCitizenPhotoSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        cityCitizenPhoto = createEntity(em);
    }

    @Test
    @Transactional
    void createCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeCreate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        // Create the CityCitizenPhoto
        restCityCitizenPhotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isCreated());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        CityCitizenPhoto testCityCitizenPhoto = cityCitizenPhotoList.get(cityCitizenPhotoList.size() - 1);
        assertThat(testCityCitizenPhoto.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCityCitizenPhoto.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCityCitizenPhotoWithExistingId() throws Exception {
        // Create the CityCitizenPhoto with an existing ID
        cityCitizenPhoto.setId(1L);

        int databaseSizeBeforeCreate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityCitizenPhotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllCityCitizenPhotos() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);

        // Get all the cityCitizenPhotoList
        restCityCitizenPhotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityCitizenPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getCityCitizenPhoto() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);

        // Get the cityCitizenPhoto
        restCityCitizenPhotoMockMvc
            .perform(get(ENTITY_API_URL_ID, cityCitizenPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityCitizenPhoto.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingCityCitizenPhoto() throws Exception {
        // Get the cityCitizenPhoto
        restCityCitizenPhotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityCitizenPhoto() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);

        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        cityCitizenPhotoSearchRepository.save(cityCitizenPhoto);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());

        // Update the cityCitizenPhoto
        CityCitizenPhoto updatedCityCitizenPhoto = cityCitizenPhotoRepository.findById(cityCitizenPhoto.getId()).get();
        // Disconnect from session so that the updates on updatedCityCitizenPhoto are not directly saved in db
        em.detach(updatedCityCitizenPhoto);
        updatedCityCitizenPhoto.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCityCitizenPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCityCitizenPhoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCityCitizenPhoto))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenPhoto testCityCitizenPhoto = cityCitizenPhotoList.get(cityCitizenPhotoList.size() - 1);
        assertThat(testCityCitizenPhoto.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCityCitizenPhoto.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<CityCitizenPhoto> cityCitizenPhotoSearchList = IterableUtils.toList(cityCitizenPhotoSearchRepository.findAll());
                CityCitizenPhoto testCityCitizenPhotoSearch = cityCitizenPhotoSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCityCitizenPhotoSearch.getImage()).isEqualTo(UPDATED_IMAGE);
                assertThat(testCityCitizenPhotoSearch.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
            });
    }

    @Test
    @Transactional
    void putNonExistingCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityCitizenPhoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCityCitizenPhotoWithPatch() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);

        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();

        // Update the cityCitizenPhoto using partial update
        CityCitizenPhoto partialUpdatedCityCitizenPhoto = new CityCitizenPhoto();
        partialUpdatedCityCitizenPhoto.setId(cityCitizenPhoto.getId());

        restCityCitizenPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityCitizenPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityCitizenPhoto))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenPhoto testCityCitizenPhoto = cityCitizenPhotoList.get(cityCitizenPhotoList.size() - 1);
        assertThat(testCityCitizenPhoto.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCityCitizenPhoto.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCityCitizenPhotoWithPatch() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);

        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();

        // Update the cityCitizenPhoto using partial update
        CityCitizenPhoto partialUpdatedCityCitizenPhoto = new CityCitizenPhoto();
        partialUpdatedCityCitizenPhoto.setId(cityCitizenPhoto.getId());

        partialUpdatedCityCitizenPhoto.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCityCitizenPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityCitizenPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityCitizenPhoto))
            )
            .andExpect(status().isOk());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        CityCitizenPhoto testCityCitizenPhoto = cityCitizenPhotoList.get(cityCitizenPhotoList.size() - 1);
        assertThat(testCityCitizenPhoto.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCityCitizenPhoto.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityCitizenPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityCitizenPhoto() throws Exception {
        int databaseSizeBeforeUpdate = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        cityCitizenPhoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityCitizenPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityCitizenPhoto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityCitizenPhoto in the database
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteCityCitizenPhoto() throws Exception {
        // Initialize the database
        cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);
        cityCitizenPhotoRepository.save(cityCitizenPhoto);
        cityCitizenPhotoSearchRepository.save(cityCitizenPhoto);

        int databaseSizeBeforeDelete = cityCitizenPhotoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the cityCitizenPhoto
        restCityCitizenPhotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityCitizenPhoto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityCitizenPhoto> cityCitizenPhotoList = cityCitizenPhotoRepository.findAll();
        assertThat(cityCitizenPhotoList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(cityCitizenPhotoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchCityCitizenPhoto() throws Exception {
        // Initialize the database
        cityCitizenPhoto = cityCitizenPhotoRepository.saveAndFlush(cityCitizenPhoto);
        cityCitizenPhotoSearchRepository.save(cityCitizenPhoto);

        // Search the cityCitizenPhoto
        restCityCitizenPhotoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cityCitizenPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityCitizenPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
}
