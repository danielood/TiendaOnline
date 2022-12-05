package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TiendaOnlineApp;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.mapper.VentaMapper;
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

/**
 * Integration tests for the {@Link VentaResource} REST controller.
 */
@SpringBootTest(classes = TiendaOnlineApp.class)
public class VentaResourceIT {

    private static final LocalDate DEFAULT_FECHA_VENTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENTA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRECIO_VENTA = 1D;
    private static final Double UPDATED_PRECIO_VENTA = 2D;

    @Autowired
    private VentaRepository ventaRepository;

    @Mock
    private VentaRepository ventaRepositoryMock;

    @Autowired
    private VentaMapper ventaMapper;

    @Mock
    private VentaService ventaServiceMock;

    @Autowired
    private VentaService ventaService;

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

    private MockMvc restVentaMockMvc;

    private Venta venta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VentaResource ventaResource = new VentaResource(ventaService);
        this.restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
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
    public static Venta createEntity(EntityManager em) {
        Venta venta = new Venta()
            .fechaVenta(DEFAULT_FECHA_VENTA)
            .precioVenta(DEFAULT_PRECIO_VENTA);
        return venta;
    }

    @BeforeEach
    public void initTest() {
        venta = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenta() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate + 1);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFechaVenta()).isEqualTo(DEFAULT_FECHA_VENTA);
        assertThat(testVenta.getPrecioVenta()).isEqualTo(DEFAULT_PRECIO_VENTA);
    }

    @Test
    @Transactional
    public void createVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta with an existing ID
        venta.setId(1L);
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaVenta").value(hasItem(DEFAULT_FECHA_VENTA.toString())))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVentasWithEagerRelationshipsIsEnabled() throws Exception {
        VentaResource ventaResource = new VentaResource(ventaServiceMock);
        when(ventaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVentaMockMvc.perform(get("/api/ventas?eagerload=true"))
        .andExpect(status().isOk());

        verify(ventaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVentasWithEagerRelationshipsIsNotEnabled() throws Exception {
        VentaResource ventaResource = new VentaResource(ventaServiceMock);
            when(ventaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVentaMockMvc.perform(get("/api/ventas?eagerload=true"))
        .andExpect(status().isOk());

            verify(ventaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venta.getId().intValue()))
            .andExpect(jsonPath("$.fechaVenta").value(DEFAULT_FECHA_VENTA.toString()))
            .andExpect(jsonPath("$.precioVenta").value(DEFAULT_PRECIO_VENTA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVenta() throws Exception {
        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Update the venta
        Venta updatedVenta = ventaRepository.findById(venta.getId()).get();
        // Disconnect from session so that the updates on updatedVenta are not directly saved in db
        em.detach(updatedVenta);
        updatedVenta
            .fechaVenta(UPDATED_FECHA_VENTA)
            .precioVenta(UPDATED_PRECIO_VENTA);
        VentaDTO ventaDTO = ventaMapper.toDto(updatedVenta);

        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isOk());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getFechaVenta()).isEqualTo(UPDATED_FECHA_VENTA);
        assertThat(testVenta.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
    }

    @Test
    @Transactional
    public void updateNonExistingVenta() throws Exception {
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeDelete = ventaRepository.findAll().size();

        // Delete the venta
        restVentaMockMvc.perform(delete("/api/ventas/{id}", venta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);
        venta2.setId(2L);
        assertThat(venta1).isNotEqualTo(venta2);
        venta1.setId(null);
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDTO.class);
        VentaDTO ventaDTO1 = new VentaDTO();
        ventaDTO1.setId(1L);
        VentaDTO ventaDTO2 = new VentaDTO();
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO2.setId(ventaDTO1.getId());
        assertThat(ventaDTO1).isEqualTo(ventaDTO2);
        ventaDTO2.setId(2L);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO1.setId(null);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ventaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ventaMapper.fromId(null)).isNull();
    }
}
