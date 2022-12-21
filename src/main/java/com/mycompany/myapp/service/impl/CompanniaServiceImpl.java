package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CompanniaService;
import com.mycompany.myapp.domain.Compannia;
import com.mycompany.myapp.repository.CompanniaRepository;
import com.mycompany.myapp.service.dto.CompanniaDTO;
import com.mycompany.myapp.service.mapper.CompanniaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Compannia}.
 */
@Service
@Transactional
public class CompanniaServiceImpl implements CompanniaService {

    private final Logger log = LoggerFactory.getLogger(CompanniaServiceImpl.class);

    private final CompanniaRepository companniaRepository;

    private final CompanniaMapper companniaMapper;

    public CompanniaServiceImpl(CompanniaRepository companniaRepository, CompanniaMapper companniaMapper) {
        this.companniaRepository = companniaRepository;
        this.companniaMapper = companniaMapper;
    }

    /**
     * Save a compannia.
     *
     * @param companniaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanniaDTO save(CompanniaDTO companniaDTO) {
        log.debug("Request to save Compannia : {}", companniaDTO);
        Compannia compannia = companniaMapper.toEntity(companniaDTO);
        compannia = companniaRepository.save(compannia);
        return companniaMapper.toDto(compannia);
    }

    /**
     * Get all the compannias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanniaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Compannias");
        return companniaRepository.findAll(pageable)
            .map(companniaMapper::toDto);
    }


    /**
     * Get one compannia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanniaDTO> findOne(Long id) {
        log.debug("Request to get Compannia : {}", id);
        return companniaRepository.findById(id)
            .map(companniaMapper::toDto);
    }

    /**
     * Delete the compannia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compannia : {}", id);
        companniaRepository.deleteById(id);
    }

    @Override
    public List<CompanniaDTO> getAll() {
        return Optional.of(this.companniaRepository.findAll()).map(this.companniaMapper :: toDto).get();
    }
}
