package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Imagen;
import com.mycompany.myapp.repository.ImagenRepository;
import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.service.dto.ImagenDTO;
import com.mycompany.myapp.service.mapper.ImagenMapper;
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
 * Integration tests for the {@Link ImagenResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class ImagenResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ImagenMapper imagenMapper;

    @Autowired
    private ImagenService imagenService;

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

    private MockMvc restImagenMockMvc;

    private Imagen imagen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagenResource imagenResource = new ImagenResource(imagenService);
        this.restImagenMockMvc = MockMvcBuilders.standaloneSetup(imagenResource)
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
    public static Imagen createEntity(EntityManager em) {
        Imagen imagen = new Imagen()
            .path(DEFAULT_PATH);
        return imagen;
    }

    @BeforeEach
    public void initTest() {
        imagen = createEntity(em);
    }

    @Test
    @Transactional
    public void createImagen() throws Exception {
        int databaseSizeBeforeCreate = imagenRepository.findAll().size();

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);
        restImagenMockMvc.perform(post("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isCreated());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate + 1);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createImagenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagenRepository.findAll().size();

        // Create the Imagen with an existing ID
        imagen.setId(1L);
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagenMockMvc.perform(post("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImagens() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get all the imagenList
        restImagenMockMvc.perform(get("/api/imagens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }
    
    @Test
    @Transactional
    public void getImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        // Get the imagen
        restImagenMockMvc.perform(get("/api/imagens/{id}", imagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imagen.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImagen() throws Exception {
        // Get the imagen
        restImagenMockMvc.perform(get("/api/imagens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Update the imagen
        Imagen updatedImagen = imagenRepository.findById(imagen.getId()).get();
        // Disconnect from session so that the updates on updatedImagen are not directly saved in db
        em.detach(updatedImagen);
        updatedImagen
            .path(UPDATED_PATH);
        ImagenDTO imagenDTO = imagenMapper.toDto(updatedImagen);

        restImagenMockMvc.perform(put("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isOk());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
        Imagen testImagen = imagenList.get(imagenList.size() - 1);
        assertThat(testImagen.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingImagen() throws Exception {
        int databaseSizeBeforeUpdate = imagenRepository.findAll().size();

        // Create the Imagen
        ImagenDTO imagenDTO = imagenMapper.toDto(imagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagenMockMvc.perform(put("/api/imagens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagen in the database
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImagen() throws Exception {
        // Initialize the database
        imagenRepository.saveAndFlush(imagen);

        int databaseSizeBeforeDelete = imagenRepository.findAll().size();

        // Delete the imagen
        restImagenMockMvc.perform(delete("/api/imagens/{id}", imagen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Imagen> imagenList = imagenRepository.findAll();
        assertThat(imagenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imagen.class);
        Imagen imagen1 = new Imagen();
        imagen1.setId(1L);
        Imagen imagen2 = new Imagen();
        imagen2.setId(imagen1.getId());
        assertThat(imagen1).isEqualTo(imagen2);
        imagen2.setId(2L);
        assertThat(imagen1).isNotEqualTo(imagen2);
        imagen1.setId(null);
        assertThat(imagen1).isNotEqualTo(imagen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagenDTO.class);
        ImagenDTO imagenDTO1 = new ImagenDTO();
        imagenDTO1.setId(1L);
        ImagenDTO imagenDTO2 = new ImagenDTO();
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
        imagenDTO2.setId(imagenDTO1.getId());
        assertThat(imagenDTO1).isEqualTo(imagenDTO2);
        imagenDTO2.setId(2L);
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
        imagenDTO1.setId(null);
        assertThat(imagenDTO1).isNotEqualTo(imagenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imagenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imagenMapper.fromId(null)).isNull();
    }
}
