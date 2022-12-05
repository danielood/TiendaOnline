package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Valoraciones;
import com.mycompany.myapp.repository.ValoracionesRepository;
import com.mycompany.myapp.service.ValoracionesService;
import com.mycompany.myapp.service.dto.ValoracionesDTO;
import com.mycompany.myapp.service.mapper.ValoracionesMapper;
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
 * Integration tests for the {@Link ValoracionesResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class ValoracionesResourceIT {

    private static final Integer DEFAULT_PUNTUACION = 1;
    private static final Integer UPDATED_PUNTUACION = 2;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private ValoracionesRepository valoracionesRepository;

    @Autowired
    private ValoracionesMapper valoracionesMapper;

    @Autowired
    private ValoracionesService valoracionesService;

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

    private MockMvc restValoracionesMockMvc;

    private Valoraciones valoraciones;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValoracionesResource valoracionesResource = new ValoracionesResource(valoracionesService);
        this.restValoracionesMockMvc = MockMvcBuilders.standaloneSetup(valoracionesResource)
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
    public static Valoraciones createEntity(EntityManager em) {
        Valoraciones valoraciones = new Valoraciones()
            .puntuacion(DEFAULT_PUNTUACION)
            .comentario(DEFAULT_COMENTARIO);
        return valoraciones;
    }

    @BeforeEach
    public void initTest() {
        valoraciones = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoraciones() throws Exception {
        int databaseSizeBeforeCreate = valoracionesRepository.findAll().size();

        // Create the Valoraciones
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);
        restValoracionesMockMvc.perform(post("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeCreate + 1);
        Valoraciones testValoraciones = valoracionesList.get(valoracionesList.size() - 1);
        assertThat(testValoraciones.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testValoraciones.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createValoracionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valoracionesRepository.findAll().size();

        // Create the Valoraciones with an existing ID
        valoraciones.setId(1L);
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValoracionesMockMvc.perform(post("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.saveAndFlush(valoraciones);

        // Get all the valoracionesList
        restValoracionesMockMvc.perform(get("/api/valoraciones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoraciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.saveAndFlush(valoraciones);

        // Get the valoraciones
        restValoracionesMockMvc.perform(get("/api/valoraciones/{id}", valoraciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoraciones.getId().intValue()))
            .andExpect(jsonPath("$.puntuacion").value(DEFAULT_PUNTUACION))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValoraciones() throws Exception {
        // Get the valoraciones
        restValoracionesMockMvc.perform(get("/api/valoraciones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.saveAndFlush(valoraciones);

        int databaseSizeBeforeUpdate = valoracionesRepository.findAll().size();

        // Update the valoraciones
        Valoraciones updatedValoraciones = valoracionesRepository.findById(valoraciones.getId()).get();
        // Disconnect from session so that the updates on updatedValoraciones are not directly saved in db
        em.detach(updatedValoraciones);
        updatedValoraciones
            .puntuacion(UPDATED_PUNTUACION)
            .comentario(UPDATED_COMENTARIO);
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(updatedValoraciones);

        restValoracionesMockMvc.perform(put("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isOk());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeUpdate);
        Valoraciones testValoraciones = valoracionesList.get(valoracionesList.size() - 1);
        assertThat(testValoraciones.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testValoraciones.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingValoraciones() throws Exception {
        int databaseSizeBeforeUpdate = valoracionesRepository.findAll().size();

        // Create the Valoraciones
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValoracionesMockMvc.perform(put("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.saveAndFlush(valoraciones);

        int databaseSizeBeforeDelete = valoracionesRepository.findAll().size();

        // Delete the valoraciones
        restValoracionesMockMvc.perform(delete("/api/valoraciones/{id}", valoraciones.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valoraciones.class);
        Valoraciones valoraciones1 = new Valoraciones();
        valoraciones1.setId(1L);
        Valoraciones valoraciones2 = new Valoraciones();
        valoraciones2.setId(valoraciones1.getId());
        assertThat(valoraciones1).isEqualTo(valoraciones2);
        valoraciones2.setId(2L);
        assertThat(valoraciones1).isNotEqualTo(valoraciones2);
        valoraciones1.setId(null);
        assertThat(valoraciones1).isNotEqualTo(valoraciones2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValoracionesDTO.class);
        ValoracionesDTO valoracionesDTO1 = new ValoracionesDTO();
        valoracionesDTO1.setId(1L);
        ValoracionesDTO valoracionesDTO2 = new ValoracionesDTO();
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
        valoracionesDTO2.setId(valoracionesDTO1.getId());
        assertThat(valoracionesDTO1).isEqualTo(valoracionesDTO2);
        valoracionesDTO2.setId(2L);
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
        valoracionesDTO1.setId(null);
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(valoracionesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(valoracionesMapper.fromId(null)).isNull();
    }
}
