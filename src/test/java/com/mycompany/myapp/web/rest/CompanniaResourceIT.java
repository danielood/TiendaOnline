package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Compannia;
import com.mycompany.myapp.repository.CompanniaRepository;
import com.mycompany.myapp.service.CompanniaService;
import com.mycompany.myapp.service.dto.CompanniaDTO;
import com.mycompany.myapp.service.mapper.CompanniaMapper;
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
 * Integration tests for the {@Link CompanniaResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class CompanniaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private CompanniaRepository companniaRepository;

    @Autowired
    private CompanniaMapper companniaMapper;

    @Autowired
    private CompanniaService companniaService;

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

    private MockMvc restCompanniaMockMvc;

    private Compannia compannia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanniaResource companniaResource = new CompanniaResource(companniaService);
        this.restCompanniaMockMvc = MockMvcBuilders.standaloneSetup(companniaResource)
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
    public static Compannia createEntity(EntityManager em) {
        Compannia compannia = new Compannia()
            .nombre(DEFAULT_NOMBRE);
        return compannia;
    }

    @BeforeEach
    public void initTest() {
        compannia = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompannia() throws Exception {
        int databaseSizeBeforeCreate = companniaRepository.findAll().size();

        // Create the Compannia
        CompanniaDTO companniaDTO = companniaMapper.toDto(compannia);
        restCompanniaMockMvc.perform(post("/api/compannias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companniaDTO)))
            .andExpect(status().isCreated());

        // Validate the Compannia in the database
        List<Compannia> companniaList = companniaRepository.findAll();
        assertThat(companniaList).hasSize(databaseSizeBeforeCreate + 1);
        Compannia testCompannia = companniaList.get(companniaList.size() - 1);
        assertThat(testCompannia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createCompanniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companniaRepository.findAll().size();

        // Create the Compannia with an existing ID
        compannia.setId(1L);
        CompanniaDTO companniaDTO = companniaMapper.toDto(compannia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanniaMockMvc.perform(post("/api/compannias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compannia in the database
        List<Compannia> companniaList = companniaRepository.findAll();
        assertThat(companniaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompannias() throws Exception {
        // Initialize the database
        companniaRepository.saveAndFlush(compannia);

        // Get all the companniaList
        restCompanniaMockMvc.perform(get("/api/compannias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compannia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompannia() throws Exception {
        // Initialize the database
        companniaRepository.saveAndFlush(compannia);

        // Get the compannia
        restCompanniaMockMvc.perform(get("/api/compannias/{id}", compannia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compannia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompannia() throws Exception {
        // Get the compannia
        restCompanniaMockMvc.perform(get("/api/compannias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompannia() throws Exception {
        // Initialize the database
        companniaRepository.saveAndFlush(compannia);

        int databaseSizeBeforeUpdate = companniaRepository.findAll().size();

        // Update the compannia
        Compannia updatedCompannia = companniaRepository.findById(compannia.getId()).get();
        // Disconnect from session so that the updates on updatedCompannia are not directly saved in db
        em.detach(updatedCompannia);
        updatedCompannia
            .nombre(UPDATED_NOMBRE);
        CompanniaDTO companniaDTO = companniaMapper.toDto(updatedCompannia);

        restCompanniaMockMvc.perform(put("/api/compannias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companniaDTO)))
            .andExpect(status().isOk());

        // Validate the Compannia in the database
        List<Compannia> companniaList = companniaRepository.findAll();
        assertThat(companniaList).hasSize(databaseSizeBeforeUpdate);
        Compannia testCompannia = companniaList.get(companniaList.size() - 1);
        assertThat(testCompannia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompannia() throws Exception {
        int databaseSizeBeforeUpdate = companniaRepository.findAll().size();

        // Create the Compannia
        CompanniaDTO companniaDTO = companniaMapper.toDto(compannia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanniaMockMvc.perform(put("/api/compannias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compannia in the database
        List<Compannia> companniaList = companniaRepository.findAll();
        assertThat(companniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompannia() throws Exception {
        // Initialize the database
        companniaRepository.saveAndFlush(compannia);

        int databaseSizeBeforeDelete = companniaRepository.findAll().size();

        // Delete the compannia
        restCompanniaMockMvc.perform(delete("/api/compannias/{id}", compannia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Compannia> companniaList = companniaRepository.findAll();
        assertThat(companniaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compannia.class);
        Compannia compannia1 = new Compannia();
        compannia1.setId(1L);
        Compannia compannia2 = new Compannia();
        compannia2.setId(compannia1.getId());
        assertThat(compannia1).isEqualTo(compannia2);
        compannia2.setId(2L);
        assertThat(compannia1).isNotEqualTo(compannia2);
        compannia1.setId(null);
        assertThat(compannia1).isNotEqualTo(compannia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanniaDTO.class);
        CompanniaDTO companniaDTO1 = new CompanniaDTO();
        companniaDTO1.setId(1L);
        CompanniaDTO companniaDTO2 = new CompanniaDTO();
        assertThat(companniaDTO1).isNotEqualTo(companniaDTO2);
        companniaDTO2.setId(companniaDTO1.getId());
        assertThat(companniaDTO1).isEqualTo(companniaDTO2);
        companniaDTO2.setId(2L);
        assertThat(companniaDTO1).isNotEqualTo(companniaDTO2);
        companniaDTO1.setId(null);
        assertThat(companniaDTO1).isNotEqualTo(companniaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companniaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companniaMapper.fromId(null)).isNull();
    }
}
