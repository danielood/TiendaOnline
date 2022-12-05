package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.repository.VideoJuegosRepository;
import com.mycompany.myapp.service.VideoJuegosService;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;
import com.mycompany.myapp.service.mapper.VideoJuegosMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Pegi;
/**
 * Integration tests for the {@Link VideoJuegosResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class VideoJuegosResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SINOPSIS = "AAAAAAAAAA";
    private static final String UPDATED_SINOPSIS = "BBBBBBBBBB";

    private static final Pegi DEFAULT_PEGI = Pegi.PEGI3;
    private static final Pegi UPDATED_PEGI = Pegi.PEGI7;

    private static final LocalDate DEFAULT_FECHA_LANZAMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LANZAMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final Boolean DEFAULT_DESTACADO = false;
    private static final Boolean UPDATED_DESTACADO = true;

    @Autowired
    private VideoJuegosRepository videoJuegosRepository;

    @Mock
    private VideoJuegosRepository videoJuegosRepositoryMock;

    @Autowired
    private VideoJuegosMapper videoJuegosMapper;

    @Mock
    private VideoJuegosService videoJuegosServiceMock;

    @Autowired
    private VideoJuegosService videoJuegosService;

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

    private MockMvc restVideoJuegosMockMvc;

    private VideoJuegos videoJuegos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VideoJuegosResource videoJuegosResource = new VideoJuegosResource(videoJuegosService);
        this.restVideoJuegosMockMvc = MockMvcBuilders.standaloneSetup(videoJuegosResource)
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
    public static VideoJuegos createEntity(EntityManager em) {
        VideoJuegos videoJuegos = new VideoJuegos()
            .titulo(DEFAULT_TITULO)
            .sinopsis(DEFAULT_SINOPSIS)
            .pegi(DEFAULT_PEGI)
            .fechaLanzamiento(DEFAULT_FECHA_LANZAMIENTO)
            .precio(DEFAULT_PRECIO)
            .stock(DEFAULT_STOCK)
            .destacado(DEFAULT_DESTACADO);
        return videoJuegos;
    }

    @BeforeEach
    public void initTest() {
        videoJuegos = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoJuegos() throws Exception {
        int databaseSizeBeforeCreate = videoJuegosRepository.findAll().size();

        // Create the VideoJuegos
        VideoJuegosDTO videoJuegosDTO = videoJuegosMapper.toDto(videoJuegos);
        restVideoJuegosMockMvc.perform(post("/api/video-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoJuegosDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoJuegos in the database
        List<VideoJuegos> videoJuegosList = videoJuegosRepository.findAll();
        assertThat(videoJuegosList).hasSize(databaseSizeBeforeCreate + 1);
        VideoJuegos testVideoJuegos = videoJuegosList.get(videoJuegosList.size() - 1);
        assertThat(testVideoJuegos.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testVideoJuegos.getSinopsis()).isEqualTo(DEFAULT_SINOPSIS);
        assertThat(testVideoJuegos.getPegi()).isEqualTo(DEFAULT_PEGI);
        assertThat(testVideoJuegos.getFechaLanzamiento()).isEqualTo(DEFAULT_FECHA_LANZAMIENTO);
        assertThat(testVideoJuegos.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testVideoJuegos.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testVideoJuegos.isDestacado()).isEqualTo(DEFAULT_DESTACADO);
    }

    @Test
    @Transactional
    public void createVideoJuegosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoJuegosRepository.findAll().size();

        // Create the VideoJuegos with an existing ID
        videoJuegos.setId(1L);
        VideoJuegosDTO videoJuegosDTO = videoJuegosMapper.toDto(videoJuegos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoJuegosMockMvc.perform(post("/api/video-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoJuegosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VideoJuegos in the database
        List<VideoJuegos> videoJuegosList = videoJuegosRepository.findAll();
        assertThat(videoJuegosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVideoJuegos() throws Exception {
        // Initialize the database
        videoJuegosRepository.saveAndFlush(videoJuegos);

        // Get all the videoJuegosList
        restVideoJuegosMockMvc.perform(get("/api/video-juegos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoJuegos.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].sinopsis").value(hasItem(DEFAULT_SINOPSIS.toString())))
            .andExpect(jsonPath("$.[*].pegi").value(hasItem(DEFAULT_PEGI.toString())))
            .andExpect(jsonPath("$.[*].fechaLanzamiento").value(hasItem(DEFAULT_FECHA_LANZAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].destacado").value(hasItem(DEFAULT_DESTACADO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVideoJuegosWithEagerRelationshipsIsEnabled() throws Exception {
        VideoJuegosResource videoJuegosResource = new VideoJuegosResource(videoJuegosServiceMock);
        when(videoJuegosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVideoJuegosMockMvc = MockMvcBuilders.standaloneSetup(videoJuegosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVideoJuegosMockMvc.perform(get("/api/video-juegos?eagerload=true"))
        .andExpect(status().isOk());

        verify(videoJuegosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVideoJuegosWithEagerRelationshipsIsNotEnabled() throws Exception {
        VideoJuegosResource videoJuegosResource = new VideoJuegosResource(videoJuegosServiceMock);
            when(videoJuegosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVideoJuegosMockMvc = MockMvcBuilders.standaloneSetup(videoJuegosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVideoJuegosMockMvc.perform(get("/api/video-juegos?eagerload=true"))
        .andExpect(status().isOk());

            verify(videoJuegosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVideoJuegos() throws Exception {
        // Initialize the database
        videoJuegosRepository.saveAndFlush(videoJuegos);

        // Get the videoJuegos
        restVideoJuegosMockMvc.perform(get("/api/video-juegos/{id}", videoJuegos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(videoJuegos.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.sinopsis").value(DEFAULT_SINOPSIS.toString()))
            .andExpect(jsonPath("$.pegi").value(DEFAULT_PEGI.toString()))
            .andExpect(jsonPath("$.fechaLanzamiento").value(DEFAULT_FECHA_LANZAMIENTO.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.destacado").value(DEFAULT_DESTACADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVideoJuegos() throws Exception {
        // Get the videoJuegos
        restVideoJuegosMockMvc.perform(get("/api/video-juegos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoJuegos() throws Exception {
        // Initialize the database
        videoJuegosRepository.saveAndFlush(videoJuegos);

        int databaseSizeBeforeUpdate = videoJuegosRepository.findAll().size();

        // Update the videoJuegos
        VideoJuegos updatedVideoJuegos = videoJuegosRepository.findById(videoJuegos.getId()).get();
        // Disconnect from session so that the updates on updatedVideoJuegos are not directly saved in db
        em.detach(updatedVideoJuegos);
        updatedVideoJuegos
            .titulo(UPDATED_TITULO)
            .sinopsis(UPDATED_SINOPSIS)
            .pegi(UPDATED_PEGI)
            .fechaLanzamiento(UPDATED_FECHA_LANZAMIENTO)
            .precio(UPDATED_PRECIO)
            .stock(UPDATED_STOCK)
            .destacado(UPDATED_DESTACADO);
        VideoJuegosDTO videoJuegosDTO = videoJuegosMapper.toDto(updatedVideoJuegos);

        restVideoJuegosMockMvc.perform(put("/api/video-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoJuegosDTO)))
            .andExpect(status().isOk());

        // Validate the VideoJuegos in the database
        List<VideoJuegos> videoJuegosList = videoJuegosRepository.findAll();
        assertThat(videoJuegosList).hasSize(databaseSizeBeforeUpdate);
        VideoJuegos testVideoJuegos = videoJuegosList.get(videoJuegosList.size() - 1);
        assertThat(testVideoJuegos.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testVideoJuegos.getSinopsis()).isEqualTo(UPDATED_SINOPSIS);
        assertThat(testVideoJuegos.getPegi()).isEqualTo(UPDATED_PEGI);
        assertThat(testVideoJuegos.getFechaLanzamiento()).isEqualTo(UPDATED_FECHA_LANZAMIENTO);
        assertThat(testVideoJuegos.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testVideoJuegos.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testVideoJuegos.isDestacado()).isEqualTo(UPDATED_DESTACADO);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoJuegos() throws Exception {
        int databaseSizeBeforeUpdate = videoJuegosRepository.findAll().size();

        // Create the VideoJuegos
        VideoJuegosDTO videoJuegosDTO = videoJuegosMapper.toDto(videoJuegos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoJuegosMockMvc.perform(put("/api/video-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoJuegosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VideoJuegos in the database
        List<VideoJuegos> videoJuegosList = videoJuegosRepository.findAll();
        assertThat(videoJuegosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideoJuegos() throws Exception {
        // Initialize the database
        videoJuegosRepository.saveAndFlush(videoJuegos);

        int databaseSizeBeforeDelete = videoJuegosRepository.findAll().size();

        // Delete the videoJuegos
        restVideoJuegosMockMvc.perform(delete("/api/video-juegos/{id}", videoJuegos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<VideoJuegos> videoJuegosList = videoJuegosRepository.findAll();
        assertThat(videoJuegosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoJuegos.class);
        VideoJuegos videoJuegos1 = new VideoJuegos();
        videoJuegos1.setId(1L);
        VideoJuegos videoJuegos2 = new VideoJuegos();
        videoJuegos2.setId(videoJuegos1.getId());
        assertThat(videoJuegos1).isEqualTo(videoJuegos2);
        videoJuegos2.setId(2L);
        assertThat(videoJuegos1).isNotEqualTo(videoJuegos2);
        videoJuegos1.setId(null);
        assertThat(videoJuegos1).isNotEqualTo(videoJuegos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoJuegosDTO.class);
        VideoJuegosDTO videoJuegosDTO1 = new VideoJuegosDTO();
        videoJuegosDTO1.setId(1L);
        VideoJuegosDTO videoJuegosDTO2 = new VideoJuegosDTO();
        assertThat(videoJuegosDTO1).isNotEqualTo(videoJuegosDTO2);
        videoJuegosDTO2.setId(videoJuegosDTO1.getId());
        assertThat(videoJuegosDTO1).isEqualTo(videoJuegosDTO2);
        videoJuegosDTO2.setId(2L);
        assertThat(videoJuegosDTO1).isNotEqualTo(videoJuegosDTO2);
        videoJuegosDTO1.setId(null);
        assertThat(videoJuegosDTO1).isNotEqualTo(videoJuegosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(videoJuegosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(videoJuegosMapper.fromId(null)).isNull();
    }
}
