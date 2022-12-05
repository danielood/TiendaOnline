package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Direccion;
import com.mycompany.myapp.repository.DireccionRepository;
import com.mycompany.myapp.service.DireccionService;
import com.mycompany.myapp.service.dto.DireccionDTO;
import com.mycompany.myapp.service.mapper.DireccionMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DireccionResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class DireccionResourceIT {

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_PORTAL = "AAAAAAAAAA";
    private static final String UPDATED_PORTAL = "BBBBBBBBBB";

    private static final String DEFAULT_ESCALERA = "AAAAAAAAAA";
    private static final String UPDATED_ESCALERA = "BBBBBBBBBB";

    private static final String DEFAULT_PISO = "AAAAAAAAAA";
    private static final String UPDATED_PISO = "BBBBBBBBBB";

    private static final String DEFAULT_LETRA = "AAAAAAAAAA";
    private static final String UPDATED_LETRA = "BBBBBBBBBB";

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private DireccionMapper direccionMapper;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDireccionMockMvc;

    private Direccion direccion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DireccionResource direccionResource = new DireccionResource(direccionService);
        this.restDireccionMockMvc = MockMvcBuilders.standaloneSetup(direccionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direccion createEntity(EntityManager em) {
        Direccion direccion = new Direccion()
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .ciudad(DEFAULT_CIUDAD)
            .calle(DEFAULT_CALLE)
            .portal(DEFAULT_PORTAL)
            .escalera(DEFAULT_ESCALERA)
            .piso(DEFAULT_PISO)
            .letra(DEFAULT_LETRA);
        return direccion;
    }

    @BeforeEach
    public void initTest() {
        direccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createDireccion() throws Exception {
        int databaseSizeBeforeCreate = direccionRepository.findAll().size();

        // Create the Direccion
        DireccionDTO direccionDTO = direccionMapper.toDto(direccion);
        restDireccionMockMvc.perform(post("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccionDTO)))
            .andExpect(status().isCreated());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate + 1);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDireccion.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDireccion.getPortal()).isEqualTo(DEFAULT_PORTAL);
        assertThat(testDireccion.getEscalera()).isEqualTo(DEFAULT_ESCALERA);
        assertThat(testDireccion.getPiso()).isEqualTo(DEFAULT_PISO);
        assertThat(testDireccion.getLetra()).isEqualTo(DEFAULT_LETRA);
    }

    @Test
    @Transactional
    public void createDireccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = direccionRepository.findAll().size();

        // Create the Direccion with an existing ID
        direccion.setId(1L);
        DireccionDTO direccionDTO = direccionMapper.toDto(direccion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireccionMockMvc.perform(post("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDireccions() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get all the direccionList
        restDireccionMockMvc.perform(get("/api/direccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD.toString())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].portal").value(hasItem(DEFAULT_PORTAL.toString())))
            .andExpect(jsonPath("$.[*].escalera").value(hasItem(DEFAULT_ESCALERA.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].letra").value(hasItem(DEFAULT_LETRA.toString())));
    }
    
    @Test
    @Transactional
    public void getDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        // Get the direccion
        restDireccionMockMvc.perform(get("/api/direccions/{id}", direccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(direccion.getId().intValue()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD.toString()))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE.toString()))
            .andExpect(jsonPath("$.portal").value(DEFAULT_PORTAL.toString()))
            .andExpect(jsonPath("$.escalera").value(DEFAULT_ESCALERA.toString()))
            .andExpect(jsonPath("$.piso").value(DEFAULT_PISO.toString()))
            .andExpect(jsonPath("$.letra").value(DEFAULT_LETRA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDireccion() throws Exception {
        // Get the direccion
        restDireccionMockMvc.perform(get("/api/direccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Update the direccion
        Direccion updatedDireccion = direccionRepository.findById(direccion.getId()).get();
        // Disconnect from session so that the updates on updatedDireccion are not directly saved in db
        em.detach(updatedDireccion);
        updatedDireccion
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .ciudad(UPDATED_CIUDAD)
            .calle(UPDATED_CALLE)
            .portal(UPDATED_PORTAL)
            .escalera(UPDATED_ESCALERA)
            .piso(UPDATED_PISO)
            .letra(UPDATED_LETRA);
        DireccionDTO direccionDTO = direccionMapper.toDto(updatedDireccion);

        restDireccionMockMvc.perform(put("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccionDTO)))
            .andExpect(status().isOk());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
        Direccion testDireccion = direccionList.get(direccionList.size() - 1);
        assertThat(testDireccion.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testDireccion.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDireccion.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testDireccion.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDireccion.getPortal()).isEqualTo(UPDATED_PORTAL);
        assertThat(testDireccion.getEscalera()).isEqualTo(UPDATED_ESCALERA);
        assertThat(testDireccion.getPiso()).isEqualTo(UPDATED_PISO);
        assertThat(testDireccion.getLetra()).isEqualTo(UPDATED_LETRA);
    }

    @Test
    @Transactional
    public void updateNonExistingDireccion() throws Exception {
        int databaseSizeBeforeUpdate = direccionRepository.findAll().size();

        // Create the Direccion
        DireccionDTO direccionDTO = direccionMapper.toDto(direccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionMockMvc.perform(put("/api/direccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(direccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Direccion in the database
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDireccion() throws Exception {
        // Initialize the database
        direccionRepository.saveAndFlush(direccion);

        int databaseSizeBeforeDelete = direccionRepository.findAll().size();

        // Delete the direccion
        restDireccionMockMvc.perform(delete("/api/direccions/{id}", direccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Direccion> direccionList = direccionRepository.findAll();
        assertThat(direccionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direccion.class);
        Direccion direccion1 = new Direccion();
        direccion1.setId(1L);
        Direccion direccion2 = new Direccion();
        direccion2.setId(direccion1.getId());
        assertThat(direccion1).isEqualTo(direccion2);
        direccion2.setId(2L);
        assertThat(direccion1).isNotEqualTo(direccion2);
        direccion1.setId(null);
        assertThat(direccion1).isNotEqualTo(direccion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DireccionDTO.class);
        DireccionDTO direccionDTO1 = new DireccionDTO();
        direccionDTO1.setId(1L);
        DireccionDTO direccionDTO2 = new DireccionDTO();
        assertThat(direccionDTO1).isNotEqualTo(direccionDTO2);
        direccionDTO2.setId(direccionDTO1.getId());
        assertThat(direccionDTO1).isEqualTo(direccionDTO2);
        direccionDTO2.setId(2L);
        assertThat(direccionDTO1).isNotEqualTo(direccionDTO2);
        direccionDTO1.setId(null);
        assertThat(direccionDTO1).isNotEqualTo(direccionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(direccionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(direccionMapper.fromId(null)).isNull();
    }
}
