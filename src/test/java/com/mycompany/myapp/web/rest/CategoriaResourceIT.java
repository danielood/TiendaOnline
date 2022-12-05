package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Categoria;
import com.mycompany.myapp.repository.CategoriaRepository;
import com.mycompany.myapp.service.CategoriaService;
import com.mycompany.myapp.service.dto.CategoriaDTO;
import com.mycompany.myapp.service.mapper.CategoriaMapper;
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
 * Integration tests for the {@Link CategoriaResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class CategoriaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private CategoriaService categoriaService;

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

    private MockMvc restCategoriaMockMvc;

    private Categoria categoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaResource categoriaResource = new CategoriaResource(categoriaService);
        this.restCategoriaMockMvc = MockMvcBuilders.standaloneSetup(categoriaResource)
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
    public static Categoria createEntity(EntityManager em) {
        Categoria categoria = new Categoria()
            .nombre(DEFAULT_NOMBRE);
        return categoria;
    }

    @BeforeEach
    public void initTest() {
        categoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoria() throws Exception {
        int databaseSizeBeforeCreate = categoriaRepository.findAll().size();

        // Create the Categoria
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);
        restCategoriaMockMvc.perform(post("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Categoria testCategoria = categoriaList.get(categoriaList.size() - 1);
        assertThat(testCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaRepository.findAll().size();

        // Create the Categoria with an existing ID
        categoria.setId(1L);
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaMockMvc.perform(post("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategorias() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categoriaList
        restCategoriaMockMvc.perform(get("/api/categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", categoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoria.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoria() throws Exception {
        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        int databaseSizeBeforeUpdate = categoriaRepository.findAll().size();

        // Update the categoria
        Categoria updatedCategoria = categoriaRepository.findById(categoria.getId()).get();
        // Disconnect from session so that the updates on updatedCategoria are not directly saved in db
        em.detach(updatedCategoria);
        updatedCategoria
            .nombre(UPDATED_NOMBRE);
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(updatedCategoria);

        restCategoriaMockMvc.perform(put("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isOk());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeUpdate);
        Categoria testCategoria = categoriaList.get(categoriaList.size() - 1);
        assertThat(testCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoria() throws Exception {
        int databaseSizeBeforeUpdate = categoriaRepository.findAll().size();

        // Create the Categoria
        CategoriaDTO categoriaDTO = categoriaMapper.toDto(categoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaMockMvc.perform(put("/api/categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categoria in the database
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        int databaseSizeBeforeDelete = categoriaRepository.findAll().size();

        // Delete the categoria
        restCategoriaMockMvc.perform(delete("/api/categorias/{id}", categoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Categoria> categoriaList = categoriaRepository.findAll();
        assertThat(categoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoria.class);
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        Categoria categoria2 = new Categoria();
        categoria2.setId(categoria1.getId());
        assertThat(categoria1).isEqualTo(categoria2);
        categoria2.setId(2L);
        assertThat(categoria1).isNotEqualTo(categoria2);
        categoria1.setId(null);
        assertThat(categoria1).isNotEqualTo(categoria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaDTO.class);
        CategoriaDTO categoriaDTO1 = new CategoriaDTO();
        categoriaDTO1.setId(1L);
        CategoriaDTO categoriaDTO2 = new CategoriaDTO();
        assertThat(categoriaDTO1).isNotEqualTo(categoriaDTO2);
        categoriaDTO2.setId(categoriaDTO1.getId());
        assertThat(categoriaDTO1).isEqualTo(categoriaDTO2);
        categoriaDTO2.setId(2L);
        assertThat(categoriaDTO1).isNotEqualTo(categoriaDTO2);
        categoriaDTO1.setId(null);
        assertThat(categoriaDTO1).isNotEqualTo(categoriaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoriaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoriaMapper.fromId(null)).isNull();
    }
}
