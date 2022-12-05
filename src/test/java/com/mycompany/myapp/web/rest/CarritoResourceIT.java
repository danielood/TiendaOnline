package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Carrito;
import com.mycompany.myapp.repository.CarritoRepository;
import com.mycompany.myapp.service.CarritoService;
import com.mycompany.myapp.service.dto.CarritoDTO;
import com.mycompany.myapp.service.mapper.CarritoMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CarritoResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class CarritoResourceIT {

    @Autowired
    private CarritoRepository carritoRepository;

    @Mock
    private CarritoRepository carritoRepositoryMock;

    @Autowired
    private CarritoMapper carritoMapper;

    @Mock
    private CarritoService carritoServiceMock;

    @Autowired
    private CarritoService carritoService;

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

    private MockMvc restCarritoMockMvc;

    private Carrito carrito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarritoResource carritoResource = new CarritoResource(carritoService);
        this.restCarritoMockMvc = MockMvcBuilders.standaloneSetup(carritoResource)
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
    public static Carrito createEntity(EntityManager em) {
        Carrito carrito = new Carrito();
        return carrito;
    }

    @BeforeEach
    public void initTest() {
        carrito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrito() throws Exception {
        int databaseSizeBeforeCreate = carritoRepository.findAll().size();

        // Create the Carrito
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);
        restCarritoMockMvc.perform(post("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeCreate + 1);
        Carrito testCarrito = carritoList.get(carritoList.size() - 1);
    }

    @Test
    @Transactional
    public void createCarritoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carritoRepository.findAll().size();

        // Create the Carrito with an existing ID
        carrito.setId(1L);
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarritoMockMvc.perform(post("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarritos() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        // Get all the carritoList
        restCarritoMockMvc.perform(get("/api/carritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrito.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCarritosWithEagerRelationshipsIsEnabled() throws Exception {
        CarritoResource carritoResource = new CarritoResource(carritoServiceMock);
        when(carritoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCarritoMockMvc = MockMvcBuilders.standaloneSetup(carritoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCarritoMockMvc.perform(get("/api/carritos?eagerload=true"))
        .andExpect(status().isOk());

        verify(carritoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCarritosWithEagerRelationshipsIsNotEnabled() throws Exception {
        CarritoResource carritoResource = new CarritoResource(carritoServiceMock);
            when(carritoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCarritoMockMvc = MockMvcBuilders.standaloneSetup(carritoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCarritoMockMvc.perform(get("/api/carritos?eagerload=true"))
        .andExpect(status().isOk());

            verify(carritoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        // Get the carrito
        restCarritoMockMvc.perform(get("/api/carritos/{id}", carrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrito.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrito() throws Exception {
        // Get the carrito
        restCarritoMockMvc.perform(get("/api/carritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        int databaseSizeBeforeUpdate = carritoRepository.findAll().size();

        // Update the carrito
        Carrito updatedCarrito = carritoRepository.findById(carrito.getId()).get();
        // Disconnect from session so that the updates on updatedCarrito are not directly saved in db
        em.detach(updatedCarrito);
        CarritoDTO carritoDTO = carritoMapper.toDto(updatedCarrito);

        restCarritoMockMvc.perform(put("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isOk());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeUpdate);
        Carrito testCarrito = carritoList.get(carritoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrito() throws Exception {
        int databaseSizeBeforeUpdate = carritoRepository.findAll().size();

        // Create the Carrito
        CarritoDTO carritoDTO = carritoMapper.toDto(carrito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarritoMockMvc.perform(put("/api/carritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carritoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrito in the database
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarrito() throws Exception {
        // Initialize the database
        carritoRepository.saveAndFlush(carrito);

        int databaseSizeBeforeDelete = carritoRepository.findAll().size();

        // Delete the carrito
        restCarritoMockMvc.perform(delete("/api/carritos/{id}", carrito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Carrito> carritoList = carritoRepository.findAll();
        assertThat(carritoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrito.class);
        Carrito carrito1 = new Carrito();
        carrito1.setId(1L);
        Carrito carrito2 = new Carrito();
        carrito2.setId(carrito1.getId());
        assertThat(carrito1).isEqualTo(carrito2);
        carrito2.setId(2L);
        assertThat(carrito1).isNotEqualTo(carrito2);
        carrito1.setId(null);
        assertThat(carrito1).isNotEqualTo(carrito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarritoDTO.class);
        CarritoDTO carritoDTO1 = new CarritoDTO();
        carritoDTO1.setId(1L);
        CarritoDTO carritoDTO2 = new CarritoDTO();
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
        carritoDTO2.setId(carritoDTO1.getId());
        assertThat(carritoDTO1).isEqualTo(carritoDTO2);
        carritoDTO2.setId(2L);
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
        carritoDTO1.setId(null);
        assertThat(carritoDTO1).isNotEqualTo(carritoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carritoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carritoMapper.fromId(null)).isNull();
    }
}
