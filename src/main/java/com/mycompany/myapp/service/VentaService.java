package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.dto.VentaTablaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Venta}.
 */
public interface VentaService {

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save.
     * @return the persisted entity.
     */
    VentaDTO save(VentaDTO ventaDTO);

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VentaTablaDTO> findAll(Pageable pageable);

    /**
     * Get all the ventas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" venta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VentaDTO> findOne(Long id);

    /**
     * Delete the "id" venta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
