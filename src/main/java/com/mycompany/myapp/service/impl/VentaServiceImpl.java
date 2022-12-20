package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.dto.VentaTablaDTO;
import com.mycompany.myapp.service.mapper.VentaMapper;
import com.mycompany.myapp.service.mapper.VentaTablaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final VentaTablaMapper ventaTablaMapper;

    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.ventaTablaMapper = new VentaTablaMapper();
    }

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VentaTablaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        Page<Venta> ventasPage = ventaRepository.findAll(pageable);
        List<VentaTablaDTO> ventaTablaDTOs = getVentaTablaDTOs(ventasPage);
        return new PageImpl<>(ventaTablaDTOs, pageable, ventasPage.getSize());
    }

    /**
     * Get all the ventas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDto);
    }


    /**
     * Get one venta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id)
            .map(ventaMapper::toDto);
    }

    /**
     * Delete the venta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

    private List<VentaTablaDTO> getVentaTablaDTOs(Page<Venta> ventasPage){
        List<VentaTablaDTO> ventaTablaDTOs = new ArrayList<>();
        List<Long> ventaIds = new ArrayList<>();
        List<Venta> ventas = new ArrayList<>();
        for(Venta venta : ventasPage){
            ventaIds.add(venta.getId());
            ventas.add(venta);
        }
        Map<Long,Long> clientMap = toMap(this.ventaRepository.findAllClientes(null));
        for(Venta venta : ventas){
            Long clienteId = clientMap.get(venta.getId());
            ventaTablaDTOs.add(this.ventaTablaMapper.toDTO(venta,clienteId));
        }
        return ventaTablaDTOs;
    }

    private Map<Long,Long> toMap(List<AuxRepository> result){
        Map<Long,Long> map = new HashMap<>();
        for(AuxRepository row : result){
            map.put(row.getId(), row.getAuxLong());
        }
        return map;
    }
}
