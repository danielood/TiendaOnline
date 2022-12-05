package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Plataforma;
import com.mycompany.myapp.repository.PlataformaRepository;
import com.mycompany.myapp.service.PlataformaService;
import com.mycompany.myapp.service.dto.PlataformaDTO;
import com.mycompany.myapp.service.mapper.PlataformaMapper;
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
 * Integration tests for the {@Link PlataformaResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class PlataformaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private PlataformaMapper plataformaMapper;

    @Autowired
    private PlataformaService plataformaService;

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

    private MockMvc restPlataformaMockMvc;

    private Plataforma plataforma;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlataformaResource plataformaResource = new PlataformaResource(plataformaService);
        this.restPlataformaMockMvc = MockMvcBuilders.standaloneSetup(plataformaResource)
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
    public static Plataforma createEntity(EntityManager em) {
        Plataforma plataforma = new Plataforma()
            .nombre(DEFAULT_NOMBRE);
        return plataforma;
    }

    @BeforeEach
    public void initTest() {
        plataforma = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlataforma() throws Exception {
        int databaseSizeBeforeCreate = plataformaRepository.findAll().size();

        // Create the Plataforma
        PlataformaDTO plataformaDTO = plataformaMapper.toDto(plataforma);
        restPlataformaMockMvc.perform(post("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plataformaDTO)))
            .andExpect(status().isCreated());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeCreate + 1);
        Plataforma testPlataforma = plataformaList.get(plataformaList.size() - 1);
        assertThat(testPlataforma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPlataformaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plataformaRepository.findAll().size();

        // Create the Plataforma with an existing ID
        plataforma.setId(1L);
        PlataformaDTO plataformaDTO = plataformaMapper.toDto(plataforma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlataformaMockMvc.perform(post("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plataformaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlataformas() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        // Get all the plataformaList
        restPlataformaMockMvc.perform(get("/api/plataformas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plataforma.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getPlataforma() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        // Get the plataforma
        restPlataformaMockMvc.perform(get("/api/plataformas/{id}", plataforma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plataforma.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlataforma() throws Exception {
        // Get the plataforma
        restPlataformaMockMvc.perform(get("/api/plataformas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlataforma() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        int databaseSizeBeforeUpdate = plataformaRepository.findAll().size();

        // Update the plataforma
        Plataforma updatedPlataforma = plataformaRepository.findById(plataforma.getId()).get();
        // Disconnect from session so that the updates on updatedPlataforma are not directly saved in db
        em.detach(updatedPlataforma);
        updatedPlataforma
            .nombre(UPDATED_NOMBRE);
        PlataformaDTO plataformaDTO = plataformaMapper.toDto(updatedPlataforma);

        restPlataformaMockMvc.perform(put("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plataformaDTO)))
            .andExpect(status().isOk());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeUpdate);
        Plataforma testPlataforma = plataformaList.get(plataformaList.size() - 1);
        assertThat(testPlataforma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlataforma() throws Exception {
        int databaseSizeBeforeUpdate = plataformaRepository.findAll().size();

        // Create the Plataforma
        PlataformaDTO plataformaDTO = plataformaMapper.toDto(plataforma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlataformaMockMvc.perform(put("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plataformaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlataforma() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        int databaseSizeBeforeDelete = plataformaRepository.findAll().size();

        // Delete the plataforma
        restPlataformaMockMvc.perform(delete("/api/plataformas/{id}", plataforma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plataforma.class);
        Plataforma plataforma1 = new Plataforma();
        plataforma1.setId(1L);
        Plataforma plataforma2 = new Plataforma();
        plataforma2.setId(plataforma1.getId());
        assertThat(plataforma1).isEqualTo(plataforma2);
        plataforma2.setId(2L);
        assertThat(plataforma1).isNotEqualTo(plataforma2);
        plataforma1.setId(null);
        assertThat(plataforma1).isNotEqualTo(plataforma2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlataformaDTO.class);
        PlataformaDTO plataformaDTO1 = new PlataformaDTO();
        plataformaDTO1.setId(1L);
        PlataformaDTO plataformaDTO2 = new PlataformaDTO();
        assertThat(plataformaDTO1).isNotEqualTo(plataformaDTO2);
        plataformaDTO2.setId(plataformaDTO1.getId());
        assertThat(plataformaDTO1).isEqualTo(plataformaDTO2);
        plataformaDTO2.setId(2L);
        assertThat(plataformaDTO1).isNotEqualTo(plataformaDTO2);
        plataformaDTO1.setId(null);
        assertThat(plataformaDTO1).isNotEqualTo(plataformaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(plataformaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(plataformaMapper.fromId(null)).isNull();
    }
}
