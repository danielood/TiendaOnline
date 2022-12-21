package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.service.PlataformaService;
import com.mycompany.myapp.service.ProductoService;
import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.Imagen;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.repository.ProductoRepository;
import com.mycompany.myapp.service.dto.ImagenDTO;
import com.mycompany.myapp.service.dto.PlataformaDTO;
import com.mycompany.myapp.service.dto.ProductoDTO;
import com.mycompany.myapp.service.dto.ProductoTablaDTO;
import com.mycompany.myapp.service.mapper.ProductoMapper;
import com.mycompany.myapp.service.mapper.ProductoTablaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ImagenService imagenService;

    private final PlataformaService plataformaService;

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductoTablaMapper productoTablaMapper;

    public ProductoServiceImpl(
        ProductoRepository productoRepository,
        ProductoMapper productoMapper,
        ImagenService imagenService,
        PlataformaService plataformaService
        ) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.imagenService = imagenService;
        this.plataformaService = plataformaService;
        this.productoTablaMapper = new ProductoTablaMapper();
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = setToSave(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoTablaDTO> findAllProductoTabla(Pageable pageable) {
        log.debug("Request to get all Productos");
        Page<Producto> productoPage = productoRepository.findAll(pageable);
        List<ProductoTablaDTO> productoTablaDTOs = getProductoTabla(productoPage);
        return new PageImpl<>(productoTablaDTOs,pageable,productoPage.getTotalElements());
    }

    @Override
    public List<ProductoDTO> findAll(){
        return Optional.of(this.productoRepository.findAllWithEagerRelationships()).map(productoMapper::toDto).get();
    }

    /**
     * Get all the productos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productoRepository.findAllWithEagerRelationships(pageable).map(productoMapper::toDto);
    }


    /**
     * Get one producto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        Optional<Producto> producto = productoRepository.findById(id);
        ProductoDTO productoDTO = productoMapper.toDto(producto.get());
        if(producto.isPresent()){
            Optional<ImagenDTO> imagenDTO = imagenService.findOne(producto.get().getImagen().getId());
            if(imagenDTO.isPresent()){
                Fichero imagen = imagenService.getFicheroFromImagen(imagenDTO.get());
                productoDTO.setImagen(imagen);
            }
        }
        return Optional.of(productoDTO);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }

    private List<ProductoTablaDTO> getProductoTabla(Page<Producto> productoPage) {
        List<Long> productoIds = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        for(Producto producto : productoPage){
            productoIds.add(producto.getId());
            productos.add(producto);
        }
        Map<Long,List<String>> plataformas = this.plataformaService.findAllByProductoId(productoIds);
        Map<Long,Fichero> imagenes = this.imagenService.findImagenes(productoIds);
        List<ProductoTablaDTO> productoTablaDTOs = new ArrayList<>();
        for(Producto producto : productos){
            List<String> plataforma = plataformas.get(producto.getId());
            Fichero imagen = imagenes.get(producto.getId());
            productoTablaDTOs.add(productoTablaMapper.toDTO(producto, imagen, plataforma));
        }
        return productoTablaDTOs;
    }

    private Producto setToSave(ProductoDTO productoDTO){
        List<PlataformaDTO> listPlataformas = productoDTO.getPlataformas();
        List<PlataformaDTO> listNuevoPlataforma = new ArrayList<>();
        List<PlataformaDTO> listEliminadosPlataforma = new ArrayList<>();

        for(PlataformaDTO plataformaDTO : listPlataformas){
            if(plataformaDTO.getId()==null){
                listEliminadosPlataforma.add(plataformaDTO);
                listNuevoPlataforma.add(this.plataformaService.save(plataformaDTO));
            }
        }
        listPlataformas.removeAll(listEliminadosPlataforma);
        listPlataformas.addAll(listNuevoPlataforma);
        productoDTO.setPlataformas(listPlataformas);

        Producto producto = productoMapper.toEntity(productoDTO);
        Optional<Imagen> opImagen = this.imagenService.findImagenFromProductoId(producto.getId());
        if(opImagen.isPresent()){
            Imagen imagen = opImagen.get();
            Fichero ficheroBBDD = this.imagenService.getFicheroFromImagen(imagen);
            Fichero ficheroImagen = productoDTO.getImagen();
            if(!ficheroBBDD.getFileBase64().equals(ficheroImagen.getFileBase64())){
                this.imagenService.delete(imagen);
            }
        }
        producto.setImagen(imagenService.save(productoDTO.getImagen()));
        return producto;
    }
}
