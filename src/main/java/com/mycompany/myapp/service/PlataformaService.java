package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PlataformaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Plataforma}.
 */
public interface PlataformaService {

    /**
     * Save a plataforma.
     *
     * @param plataformaDTO the entity to save.
     * @return the persisted entity.
     */
    PlataformaDTO save(PlataformaDTO plataformaDTO);

    /**
     * Get all the plataformas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlataformaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" plataforma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlataformaDTO> findOne(Long id);

    /**
     * Delete the "id" plataforma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
