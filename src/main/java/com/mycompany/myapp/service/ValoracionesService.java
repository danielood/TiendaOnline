package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.service.dto.ValoracionesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Valoraciones}.
 */
public interface ValoracionesService {

    /**
     * Save a valoraciones.
     *
     * @param valoracionesDTO the entity to save.
     * @return the persisted entity.
     */
    ValoracionesDTO save(ValoracionesDTO valoracionesDTO);

    /**
     * Get all the valoraciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ValoracionesDTO> findAll(Pageable pageable);

    Double getValoracionFromVideoJuegos(Long id);


    /**
     * Get the "id" valoraciones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ValoracionesDTO> findOne(Long id);

    /**
     * Delete the "id" valoraciones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
