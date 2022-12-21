package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.JuegoTablaDTO;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.VideoJuegos}.
 */
public interface VideoJuegosService {

    /**
     * Save a videoJuegos.
     *
     * @param videoJuegosDTO the entity to save.
     * @return the persisted entity.
     */
    VideoJuegosDTO save(VideoJuegosDTO videoJuegosDTO);

    /**
     * Get all the videoJuegos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JuegoTablaDTO> findAllJuegoTabla(Pageable pageable);

    /**
     * Get all the videoJuegos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<JuegoTablaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" videoJuegos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideoJuegosDTO> findOne(Long id);

    /**
     * Delete the "id" videoJuegos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<VideoJuegosDTO> findAll();
}
