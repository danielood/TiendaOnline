package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanniaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Compannia}.
 */
public interface CompanniaService {

    /**
     * Save a compannia.
     *
     * @param companniaDTO the entity to save.
     * @return the persisted entity.
     */
    CompanniaDTO save(CompanniaDTO companniaDTO);

    /**
     * Get all the compannias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanniaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" compannia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanniaDTO> findOne(Long id);

    /**
     * Delete the "id" compannia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
