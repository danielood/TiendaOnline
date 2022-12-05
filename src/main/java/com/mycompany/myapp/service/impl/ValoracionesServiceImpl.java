package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ValoracionesService;
import com.mycompany.myapp.domain.Valoraciones;
import com.mycompany.myapp.repository.ValoracionesRepository;
import com.mycompany.myapp.service.dto.ValoracionesDTO;
import com.mycompany.myapp.service.mapper.ValoracionesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Valoraciones}.
 */
@Service
@Transactional
public class ValoracionesServiceImpl implements ValoracionesService {

    private final Logger log = LoggerFactory.getLogger(ValoracionesServiceImpl.class);

    private final ValoracionesRepository valoracionesRepository;

    private final ValoracionesMapper valoracionesMapper;

    public ValoracionesServiceImpl(ValoracionesRepository valoracionesRepository, ValoracionesMapper valoracionesMapper) {
        this.valoracionesRepository = valoracionesRepository;
        this.valoracionesMapper = valoracionesMapper;
    }

    /**
     * Save a valoraciones.
     *
     * @param valoracionesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ValoracionesDTO save(ValoracionesDTO valoracionesDTO) {
        log.debug("Request to save Valoraciones : {}", valoracionesDTO);
        Valoraciones valoraciones = valoracionesMapper.toEntity(valoracionesDTO);
        valoraciones = valoracionesRepository.save(valoraciones);
        return valoracionesMapper.toDto(valoraciones);
    }

    /**
     * Get all the valoraciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ValoracionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Valoraciones");
        return valoracionesRepository.findAll(pageable)
            .map(valoracionesMapper::toDto);
    }


    /**
     * Get one valoraciones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ValoracionesDTO> findOne(Long id) {
        log.debug("Request to get Valoraciones : {}", id);
        return valoracionesRepository.findById(id)
            .map(valoracionesMapper::toDto);
    }

    /**
     * Delete the valoraciones by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Valoraciones : {}", id);
        valoracionesRepository.deleteById(id);
    }
}
