package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.service.VideoJuegosService;
import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.repository.VideoJuegosRepository;
import com.mycompany.myapp.service.dto.JuegoTablaDTO;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;
import com.mycompany.myapp.service.mapper.JuegoTablaMapper;
import com.mycompany.myapp.service.mapper.VideoJuegosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link VideoJuegos}.
 */
@Service
@Transactional
public class VideoJuegosServiceImpl implements VideoJuegosService {

    private final Logger log = LoggerFactory.getLogger(VideoJuegosServiceImpl.class);

    private final VideoJuegosRepository videoJuegosRepository;

    private final VideoJuegosMapper videoJuegosMapper;

    private final ImagenService imagenService;

    private final JuegoTablaMapper juegoTablaMapper;

    public VideoJuegosServiceImpl(VideoJuegosRepository videoJuegosRepository, VideoJuegosMapper videoJuegosMapper, ImagenService imagenService) {
        this.videoJuegosRepository = videoJuegosRepository;
        this.videoJuegosMapper = videoJuegosMapper;
        this.imagenService = imagenService;
        this.juegoTablaMapper = new JuegoTablaMapper();
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
    public Page<JuegoTablaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VideoJuegos");
        Page<VideoJuegos> videoJuegosPage = videoJuegosRepository.findAll(pageable);
        List<VideoJuegos> videoJuegos = new ArrayList<>();
        List<Long> idVideoJuegos = new ArrayList<>();
        for(VideoJuegos videoJuego : videoJuegosPage){
            idVideoJuegos.add(videoJuego.getId());
            videoJuegos.add(videoJuego);
        }
        Map<Long,String> compannies = covertAuxToMap(videoJuegosRepository.finCompannies(idVideoJuegos));
        Map<Long, Fichero> caratulas = imagenService.findCaratulas(idVideoJuegos);
        //TODO
        return null;
    }

    /**
     * Get all the videoJuegos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<JuegoTablaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return null;
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

    private Map<Long,String> covertAuxToMap(List<AuxRepository> result){
        Map<Long,String> map = new HashMap<>();
        for(AuxRepository row : result){
            map.put(row.getId(),row.getAuxString());
        }
        return map;
    }
}
