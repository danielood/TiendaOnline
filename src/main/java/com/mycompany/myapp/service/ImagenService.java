package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.Imagen;
import com.mycompany.myapp.service.dto.ImagenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Imagen}.
 */
public interface ImagenService {

    /**
     * Save a imagen.
     *
     * @param imagenDTO the entity to save.
     * @return the persisted entity.
     */
    Imagen save(Fichero fichero);

    ImagenDTO save(ImagenDTO imagenDTO);

    /**
     * Get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagenDTO> findAll(Pageable pageable);

    Map<Long, Fichero> findCaratulas(List<Long> ids);

    Optional<Imagen> findImagenFromVideoJuegoId(Long id);
    /**
     * Get the "id" imagen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImagenDTO> findOne(Long id);

    Fichero getFicheroFromImagen(ImagenDTO imagenDTO);

    Fichero getFicheroFromImagen(Imagen imagen);

    /**
     * Delete the "id" imagen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void delete(ImagenDTO imagenDTO);

    void delete(Imagen imagen);

    void deleteByVideoJuego(Long id);
}
