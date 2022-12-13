package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.service.PlataformaService;
import com.mycompany.myapp.domain.Plataforma;
import com.mycompany.myapp.repository.PlataformaRepository;
import com.mycompany.myapp.service.dto.PlataformaDTO;
import com.mycompany.myapp.service.mapper.PlataformaMapper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link Plataforma}.
 */
@Service
@Transactional
public class PlataformaServiceImpl implements PlataformaService {

    private final Logger log = LoggerFactory.getLogger(PlataformaServiceImpl.class);

    private final PlataformaRepository plataformaRepository;

    private final PlataformaMapper plataformaMapper;

    public PlataformaServiceImpl(PlataformaRepository plataformaRepository, PlataformaMapper plataformaMapper) {
        this.plataformaRepository = plataformaRepository;
        this.plataformaMapper = plataformaMapper;
    }

    /**
     * Save a plataforma.
     *
     * @param plataformaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlataformaDTO save(PlataformaDTO plataformaDTO) {
        log.debug("Request to save Plataforma : {}", plataformaDTO);
        Plataforma plataforma = plataformaMapper.toEntity(plataformaDTO);
        plataforma = plataformaRepository.save(plataforma);
        return plataformaMapper.toDto(plataforma);
    }

    /**
     * Get all the plataformas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlataformaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Plataformas");
        return plataformaRepository.findAll(pageable)
            .map(plataformaMapper::toDto);
    }


    /**
     * Get one plataforma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlataformaDTO> findOne(Long id) {
        log.debug("Request to get Plataforma : {}", id);
        return plataformaRepository.findById(id)
            .map(plataformaMapper::toDto);
    }

    /**
     * Delete the plataforma by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plataforma : {}", id);
        plataformaRepository.deleteById(id);
    }

    @Override
    public Map<Long, List<String>> findAllById(List<Long> ids) {
        Map<Long,List<String>> map = new HashMap<>();
        List<AuxRepository> result = plataformaRepository.findAllByIds(ids);
        for(AuxRepository row : result){
            map.put(row.getId(),toList(row.getAuxString()));
        }
        return map;
    }

    private List<String> toList(String stringList){
        String[] strings = stringList.split(",");
        List<String> listString = Arrays.asList(strings);
        return listString;
    }
}
