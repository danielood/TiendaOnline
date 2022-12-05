package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.domain.Imagen;
import com.mycompany.myapp.repository.ImagenRepository;
import com.mycompany.myapp.service.dto.ImagenDTO;
import com.mycompany.myapp.service.mapper.ImagenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Imagen}.
 */
@Service
@Transactional
public class ImagenServiceImpl implements ImagenService {

    private final Logger log = LoggerFactory.getLogger(ImagenServiceImpl.class);

    private final ImagenRepository imagenRepository;

    private final ImagenMapper imagenMapper;

    public ImagenServiceImpl(ImagenRepository imagenRepository, ImagenMapper imagenMapper) {
        this.imagenRepository = imagenRepository;
        this.imagenMapper = imagenMapper;
    }

    /**
     * Save a imagen.
     *
     * @param imagenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImagenDTO save(ImagenDTO imagenDTO) {
        log.debug("Request to save Imagen : {}", imagenDTO);
        Imagen imagen = imagenMapper.toEntity(imagenDTO);
        imagen = imagenRepository.save(imagen);
        return imagenMapper.toDto(imagen);
    }

    /**
     * Get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImagenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Imagens");
        return imagenRepository.findAll(pageable)
            .map(imagenMapper::toDto);
    }


    /**
     * Get one imagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImagenDTO> findOne(Long id) {
        log.debug("Request to get Imagen : {}", id);
        return imagenRepository.findById(id)
            .map(imagenMapper::toDto);
    }

    /**
     * Delete the imagen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imagen : {}", id);
        imagenRepository.deleteById(id);
    }
}
