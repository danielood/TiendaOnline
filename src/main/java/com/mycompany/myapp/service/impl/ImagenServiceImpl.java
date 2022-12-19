package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.service.FileService;
import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.domain.Imagen;
import com.mycompany.myapp.repository.ImagenRepository;
import com.mycompany.myapp.service.dto.ImagenDTO;
import com.mycompany.myapp.service.mapper.ImagenMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Service Implementation for managing {@link Imagen}.
 */
@Service
@Transactional
public class ImagenServiceImpl implements ImagenService {

    private final Logger log = LoggerFactory.getLogger(ImagenServiceImpl.class);

    private final FileService fileService;

    private final ImagenRepository imagenRepository;

    private final ImagenMapper imagenMapper;

    public ImagenServiceImpl(ImagenRepository imagenRepository, ImagenMapper imagenMapper,FileService fileService) {
        this.imagenRepository = imagenRepository;
        this.imagenMapper = imagenMapper;
        this.fileService = fileService;
    }

    /**
     * Save a imagen.
     *
     * @param imagenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Imagen save(Fichero fichero) {
        Imagen imagenToSave = new Imagen();
        imagenToSave.setPath(fileService.saveFile(fichero));
        Imagen imagen = imagenRepository.save(imagenToSave);
        log.debug("Request to save Imagen : {}",imagen);
        return imagen;
    }

    @Override
    public ImagenDTO save(ImagenDTO imagenDTO) {
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

    @Override
    public Map<Long, Fichero> findCaratulas(List<Long> ids) {
        Map<Long, Fichero> map = new HashMap<>();
        List<AuxRepository> result = imagenRepository.findCaratulas(ids);
        for(AuxRepository row : result){
            Fichero fichero = convertPathToFichero(row.getAuxString());
            map.put(row.getId(),fichero);
        }
        return map;
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

    @Override
    public void delete(ImagenDTO imagenDTO) {
        this.fileService.deleteFile(imagenDTO.getPath());
        this.imagenRepository.deleteById(imagenDTO.getId());
    }

    @Override
    public void delete(Imagen imagen) {
        this.fileService.deleteFile(imagen.getPath());
        this.imagenRepository.deleteById(imagen.getId());
    }

    private Fichero convertPathToFichero(String path){
        Fichero fichero = new Fichero();
        File file = new File(path);
        fichero.setFileName(file.getName());
        fichero.setFileType(getContentType(file));
        fichero.setFileBase64(getBase64(file));
        return fichero;
    }

    private String getBase64(File file){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private String getContentType(File file){
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Fichero getFicheroFromImagen(ImagenDTO imagenDTO) {
        return this.fileService.getFicheroFromImagen(imagenDTO.getPath());
    }

    @Override
    public Fichero getFicheroFromImagen(Imagen imagen) {
        return this.fileService.getFicheroFromImagen(imagen.getPath());
    }

    @Override
    public Optional<Imagen> findImagenFromVideoJuegoId(Long id) {
        return this.imagenRepository.findImagenFromVideoJuegoId(id);
    }

    @Override
    public void deleteByVideoJuego(Long id) {
        Optional<Imagen> imagenOpt = this.imagenRepository.findImagenFromVideoJuegoId(id);
        if(imagenOpt.isPresent()){
            this.fileService.deleteFile(imagenOpt.get().getPath());
            this.delete(imagenOpt.get());
        }
    }
}
