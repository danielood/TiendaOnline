package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VideoJuegosService;
import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.repository.VideoJuegosRepository;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;
import com.mycompany.myapp.service.mapper.VideoJuegosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VideoJuegos}.
 */
@Service
@Transactional
public class VideoJuegosServiceImpl implements VideoJuegosService {

    private final Logger log = LoggerFactory.getLogger(VideoJuegosServiceImpl.class);

    private final VideoJuegosRepository videoJuegosRepository;

    private final VideoJuegosMapper videoJuegosMapper;

    public VideoJuegosServiceImpl(VideoJuegosRepository videoJuegosRepository, VideoJuegosMapper videoJuegosMapper) {
        this.videoJuegosRepository = videoJuegosRepository;
        this.videoJuegosMapper = videoJuegosMapper;
    }

    /**
     * Save a videoJuegos.
     *
     * @param videoJuegosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VideoJuegosDTO save(VideoJuegosDTO videoJuegosDTO) {
        log.debug("Request to save VideoJuegos : {}", videoJuegosDTO);
        VideoJuegos videoJuegos = videoJuegosMapper.toEntity(videoJuegosDTO);
        videoJuegos = videoJuegosRepository.save(videoJuegos);
        return videoJuegosMapper.toDto(videoJuegos);
    }

    /**
     * Get all the videoJuegos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoJuegosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VideoJuegos");
        return videoJuegosRepository.findAll(pageable)
            .map(videoJuegosMapper::toDto);
    }

    /**
     * Get all the videoJuegos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VideoJuegosDTO> findAllWithEagerRelationships(Pageable pageable) {
        return videoJuegosRepository.findAllWithEagerRelationships(pageable).map(videoJuegosMapper::toDto);
    }
    

    /**
     * Get one videoJuegos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VideoJuegosDTO> findOne(Long id) {
        log.debug("Request to get VideoJuegos : {}", id);
        return videoJuegosRepository.findOneWithEagerRelationships(id)
            .map(videoJuegosMapper::toDto);
    }

    /**
     * Delete the videoJuegos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoJuegos : {}", id);
        videoJuegosRepository.deleteById(id);
    }
}
