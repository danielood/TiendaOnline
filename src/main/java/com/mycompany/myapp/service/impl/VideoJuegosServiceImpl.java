package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Categoria;
import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.service.CategoriaService;
import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.service.PlataformaService;
import com.mycompany.myapp.service.VideoJuegosService;
import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.repository.VideoJuegosRepository;
import com.mycompany.myapp.service.dto.CategoriaDTO;
import com.mycompany.myapp.service.dto.JuegoTablaDTO;
import com.mycompany.myapp.service.dto.PlataformaDTO;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;
import com.mycompany.myapp.service.mapper.JuegoTablaMapper;
import com.mycompany.myapp.service.mapper.VideoJuegosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    private final PlataformaService plataformaService;

    private final CategoriaService categoriaService;

    private final JuegoTablaMapper juegoTablaMapper;

    public VideoJuegosServiceImpl(VideoJuegosRepository videoJuegosRepository, VideoJuegosMapper videoJuegosMapper, ImagenService imagenService,PlataformaService plataformaService,CategoriaService categoriaService) {
        this.videoJuegosRepository = videoJuegosRepository;
        this.videoJuegosMapper = videoJuegosMapper;
        this.imagenService = imagenService;
        this.plataformaService = plataformaService;
        this.categoriaService = categoriaService;
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
        VideoJuegos videoJuegos = setToSave(videoJuegosDTO);
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
        List<JuegoTablaDTO> juegosTablaDTO = getJuegoTablaDTOS(videoJuegosPage);
        return new PageImpl<>(juegosTablaDTO,pageable,videoJuegosPage.getTotalElements());
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

    private List<JuegoTablaDTO> getJuegoTablaDTOS(Page<VideoJuegos> videoJuegosPage) {
        List<VideoJuegos> videoJuegos = new ArrayList<>();
        List<Long> idVideoJuegos = new ArrayList<>();
        for(VideoJuegos videoJuego : videoJuegosPage){
            idVideoJuegos.add(videoJuego.getId());
            videoJuegos.add(videoJuego);
        }
        List<JuegoTablaDTO> juegosTablaDTO = new ArrayList<>();
        Map<Long,String> compannies = covertAuxToMap(videoJuegosRepository.finCompannies(idVideoJuegos));
        Map<Long, Fichero> caratulas = imagenService.findCaratulas(idVideoJuegos);
        Map<Long,List<String>> plataforma = plataformaService.findAllById(idVideoJuegos);
        for(VideoJuegos videoJuego : videoJuegos){
            String compannie = compannies.get(videoJuego.getId());
            Fichero fichero = caratulas.get(videoJuego.getId());
            List<String> plataformas = plataforma.get(videoJuego.getId());
            juegosTablaDTO.add(juegoTablaMapper.toDTO(videoJuego,fichero,compannie,plataformas));
        }
        return juegosTablaDTO;
    }

    private Map<Long,String> covertAuxToMap(List<AuxRepository> result){
        Map<Long,String> map = new HashMap<>();
        for(AuxRepository row : result){
            map.put(row.getId(),row.getAuxString());
        }
        return map;
    }

    private VideoJuegos setToSave(VideoJuegosDTO videoJuegosDTO) {
        List<CategoriaDTO> setCategoriasDTO = videoJuegosDTO.getCategorias();
        List<CategoriaDTO> listNuevosCategorias = new ArrayList<>();
        List<CategoriaDTO> setEliminadosCategoria = new ArrayList<>();
        for(CategoriaDTO categoriaDTO : setCategoriasDTO){
            if(categoriaDTO.getId()==null){
                listNuevosCategorias.add(this.categoriaService.save(categoriaDTO));
                setEliminadosCategoria.add(categoriaDTO);
            }
        }
        setCategoriasDTO.removeAll(setEliminadosCategoria);
        setCategoriasDTO.addAll(listNuevosCategorias);
        videoJuegosDTO.setCategorias(setCategoriasDTO);

        List<PlataformaDTO> setPlataforma = videoJuegosDTO.getPlataformas();
        List<PlataformaDTO> listNuevosPlataforma = new ArrayList<>();
        List<PlataformaDTO> setEliminadosPlataforma = new ArrayList<>();
        for(PlataformaDTO plataformaDTO : setPlataforma){
            if(plataformaDTO.getId()==null){
                setEliminadosPlataforma.add(plataformaDTO);
                listNuevosPlataforma.add(plataformaService.save(plataformaDTO));
            }
        }
        setPlataforma.removeAll(setEliminadosPlataforma);
        setPlataforma.addAll(listNuevosPlataforma);
        videoJuegosDTO.setPlataformas(setPlataforma);
        VideoJuegos videoJuegos = videoJuegosMapper.toEntity(videoJuegosDTO);
        videoJuegos.setCaratula(imagenService.save(videoJuegosDTO.getCaratula()));
        return videoJuegos;
    }
}
