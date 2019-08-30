package app.sonarcloud.test.web.rest;

import app.sonarcloud.test.SonarcloudtestApp;

import app.sonarcloud.test.domain.ClaseDeNegocio;
import app.sonarcloud.test.repository.ClaseDeNegocioRepository;
import app.sonarcloud.test.service.ClaseDeNegocioService;
import app.sonarcloud.test.service.dto.ClaseDeNegocioDTO;
import app.sonarcloud.test.service.mapper.ClaseDeNegocioMapper;
import app.sonarcloud.test.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static app.sonarcloud.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClaseDeNegocioResource REST controller.
 *
 * @see ClaseDeNegocioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SonarcloudtestApp.class)
public class ClaseDeNegocioResourceIntTest {

    private static final String DEFAULT_UN_CAMPO = "AAAAAAAAAA";
    private static final String UPDATED_UN_CAMPO = "BBBBBBBBBB";

    private static final String DEFAULT_OTRO_CAMPO = "AAAAAAAAAA";
    private static final String UPDATED_OTRO_CAMPO = "BBBBBBBBBB";

    @Autowired
    private ClaseDeNegocioRepository claseDeNegocioRepository;

    @Autowired
    private ClaseDeNegocioMapper claseDeNegocioMapper;
    
    @Autowired
    private ClaseDeNegocioService claseDeNegocioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClaseDeNegocioMockMvc;

    private ClaseDeNegocio claseDeNegocio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClaseDeNegocioResource claseDeNegocioResource = new ClaseDeNegocioResource(claseDeNegocioService);
        this.restClaseDeNegocioMockMvc = MockMvcBuilders.standaloneSetup(claseDeNegocioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaseDeNegocio createEntity(EntityManager em) {
        ClaseDeNegocio claseDeNegocio = new ClaseDeNegocio()
            .unCampo(DEFAULT_UN_CAMPO)
            .otroCampo(DEFAULT_OTRO_CAMPO);
        return claseDeNegocio;
    }

    @Before
    public void initTest() {
        claseDeNegocio = createEntity(em);
    }

    @Test
    @Transactional
    public void createClaseDeNegocio() throws Exception {
        int databaseSizeBeforeCreate = claseDeNegocioRepository.findAll().size();

        // Create the ClaseDeNegocio
        ClaseDeNegocioDTO claseDeNegocioDTO = claseDeNegocioMapper.toDto(claseDeNegocio);
        restClaseDeNegocioMockMvc.perform(post("/api/clase-de-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDeNegocioDTO)))
            .andExpect(status().isCreated());

        // Validate the ClaseDeNegocio in the database
        List<ClaseDeNegocio> claseDeNegocioList = claseDeNegocioRepository.findAll();
        assertThat(claseDeNegocioList).hasSize(databaseSizeBeforeCreate + 1);
        ClaseDeNegocio testClaseDeNegocio = claseDeNegocioList.get(claseDeNegocioList.size() - 1);
        assertThat(testClaseDeNegocio.getUnCampo()).isEqualTo(DEFAULT_UN_CAMPO);
        assertThat(testClaseDeNegocio.getOtroCampo()).isEqualTo(DEFAULT_OTRO_CAMPO);
    }

    @Test
    @Transactional
    public void createClaseDeNegocioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claseDeNegocioRepository.findAll().size();

        // Create the ClaseDeNegocio with an existing ID
        claseDeNegocio.setId(1L);
        ClaseDeNegocioDTO claseDeNegocioDTO = claseDeNegocioMapper.toDto(claseDeNegocio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaseDeNegocioMockMvc.perform(post("/api/clase-de-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDeNegocioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClaseDeNegocio in the database
        List<ClaseDeNegocio> claseDeNegocioList = claseDeNegocioRepository.findAll();
        assertThat(claseDeNegocioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClaseDeNegocios() throws Exception {
        // Initialize the database
        claseDeNegocioRepository.saveAndFlush(claseDeNegocio);

        // Get all the claseDeNegocioList
        restClaseDeNegocioMockMvc.perform(get("/api/clase-de-negocios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claseDeNegocio.getId().intValue())))
            .andExpect(jsonPath("$.[*].unCampo").value(hasItem(DEFAULT_UN_CAMPO.toString())))
            .andExpect(jsonPath("$.[*].otroCampo").value(hasItem(DEFAULT_OTRO_CAMPO.toString())));
    }
    
    @Test
    @Transactional
    public void getClaseDeNegocio() throws Exception {
        // Initialize the database
        claseDeNegocioRepository.saveAndFlush(claseDeNegocio);

        // Get the claseDeNegocio
        restClaseDeNegocioMockMvc.perform(get("/api/clase-de-negocios/{id}", claseDeNegocio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(claseDeNegocio.getId().intValue()))
            .andExpect(jsonPath("$.unCampo").value(DEFAULT_UN_CAMPO.toString()))
            .andExpect(jsonPath("$.otroCampo").value(DEFAULT_OTRO_CAMPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClaseDeNegocio() throws Exception {
        // Get the claseDeNegocio
        restClaseDeNegocioMockMvc.perform(get("/api/clase-de-negocios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClaseDeNegocio() throws Exception {
        // Initialize the database
        claseDeNegocioRepository.saveAndFlush(claseDeNegocio);

        int databaseSizeBeforeUpdate = claseDeNegocioRepository.findAll().size();

        // Update the claseDeNegocio
        ClaseDeNegocio updatedClaseDeNegocio = claseDeNegocioRepository.findById(claseDeNegocio.getId()).get();
        // Disconnect from session so that the updates on updatedClaseDeNegocio are not directly saved in db
        em.detach(updatedClaseDeNegocio);
        updatedClaseDeNegocio
            .unCampo(UPDATED_UN_CAMPO)
            .otroCampo(UPDATED_OTRO_CAMPO);
        ClaseDeNegocioDTO claseDeNegocioDTO = claseDeNegocioMapper.toDto(updatedClaseDeNegocio);

        restClaseDeNegocioMockMvc.perform(put("/api/clase-de-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDeNegocioDTO)))
            .andExpect(status().isOk());

        // Validate the ClaseDeNegocio in the database
        List<ClaseDeNegocio> claseDeNegocioList = claseDeNegocioRepository.findAll();
        assertThat(claseDeNegocioList).hasSize(databaseSizeBeforeUpdate);
        ClaseDeNegocio testClaseDeNegocio = claseDeNegocioList.get(claseDeNegocioList.size() - 1);
        assertThat(testClaseDeNegocio.getUnCampo()).isEqualTo(UPDATED_UN_CAMPO);
        assertThat(testClaseDeNegocio.getOtroCampo()).isEqualTo(UPDATED_OTRO_CAMPO);
    }

    @Test
    @Transactional
    public void updateNonExistingClaseDeNegocio() throws Exception {
        int databaseSizeBeforeUpdate = claseDeNegocioRepository.findAll().size();

        // Create the ClaseDeNegocio
        ClaseDeNegocioDTO claseDeNegocioDTO = claseDeNegocioMapper.toDto(claseDeNegocio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaseDeNegocioMockMvc.perform(put("/api/clase-de-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDeNegocioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClaseDeNegocio in the database
        List<ClaseDeNegocio> claseDeNegocioList = claseDeNegocioRepository.findAll();
        assertThat(claseDeNegocioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClaseDeNegocio() throws Exception {
        // Initialize the database
        claseDeNegocioRepository.saveAndFlush(claseDeNegocio);

        int databaseSizeBeforeDelete = claseDeNegocioRepository.findAll().size();

        // Get the claseDeNegocio
        restClaseDeNegocioMockMvc.perform(delete("/api/clase-de-negocios/{id}", claseDeNegocio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClaseDeNegocio> claseDeNegocioList = claseDeNegocioRepository.findAll();
        assertThat(claseDeNegocioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseDeNegocio.class);
        ClaseDeNegocio claseDeNegocio1 = new ClaseDeNegocio();
        claseDeNegocio1.setId(1L);
        ClaseDeNegocio claseDeNegocio2 = new ClaseDeNegocio();
        claseDeNegocio2.setId(claseDeNegocio1.getId());
        assertThat(claseDeNegocio1).isEqualTo(claseDeNegocio2);
        claseDeNegocio2.setId(2L);
        assertThat(claseDeNegocio1).isNotEqualTo(claseDeNegocio2);
        claseDeNegocio1.setId(null);
        assertThat(claseDeNegocio1).isNotEqualTo(claseDeNegocio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseDeNegocioDTO.class);
        ClaseDeNegocioDTO claseDeNegocioDTO1 = new ClaseDeNegocioDTO();
        claseDeNegocioDTO1.setId(1L);
        ClaseDeNegocioDTO claseDeNegocioDTO2 = new ClaseDeNegocioDTO();
        assertThat(claseDeNegocioDTO1).isNotEqualTo(claseDeNegocioDTO2);
        claseDeNegocioDTO2.setId(claseDeNegocioDTO1.getId());
        assertThat(claseDeNegocioDTO1).isEqualTo(claseDeNegocioDTO2);
        claseDeNegocioDTO2.setId(2L);
        assertThat(claseDeNegocioDTO1).isNotEqualTo(claseDeNegocioDTO2);
        claseDeNegocioDTO1.setId(null);
        assertThat(claseDeNegocioDTO1).isNotEqualTo(claseDeNegocioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(claseDeNegocioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(claseDeNegocioMapper.fromId(null)).isNull();
    }
}
