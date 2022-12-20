package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Direccion;
import com.mycompany.myapp.service.dto.DireccionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Direccion}.
 */
public interface DireccionService {

    /**
     * Save a direccion.
     *
     * @param direccionDTO the entity to save.
     * @return the persisted entity.
     */
    DireccionDTO save(DireccionDTO direccionDTO);

    /**
     * Get all the direccions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DireccionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" direccion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DireccionDTO> findOne(Long id);

    /**
     * Delete the "id" direccion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<DireccionDTO> findByClientId(Long id);
}
