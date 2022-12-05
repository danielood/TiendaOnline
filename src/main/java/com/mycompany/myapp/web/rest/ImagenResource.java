package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ImagenService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ImagenDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Imagen}.
 */
@RestController
@RequestMapping("/api")
public class ImagenResource {

    private final Logger log = LoggerFactory.getLogger(ImagenResource.class);

    private static final String ENTITY_NAME = "imagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagenService imagenService;

    public ImagenResource(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    /**
     * {@code POST  /imagens} : Create a new imagen.
     *
     * @param imagenDTO the imagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagenDTO, or with status {@code 400 (Bad Request)} if the imagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/imagens")
    public ResponseEntity<ImagenDTO> createImagen(@RequestBody ImagenDTO imagenDTO) throws URISyntaxException {
        log.debug("REST request to save Imagen : {}", imagenDTO);
        if (imagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new imagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagenDTO result = imagenService.save(imagenDTO);
        return ResponseEntity.created(new URI("/api/imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /imagens} : Updates an existing imagen.
     *
     * @param imagenDTO the imagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagenDTO,
     * or with status {@code 400 (Bad Request)} if the imagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/imagens")
    public ResponseEntity<ImagenDTO> updateImagen(@RequestBody ImagenDTO imagenDTO) throws URISyntaxException {
        log.debug("REST request to update Imagen : {}", imagenDTO);
        if (imagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImagenDTO result = imagenService.save(imagenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /imagens} : get all the imagens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imagens in body.
     */
    @GetMapping("/imagens")
    public ResponseEntity<List<ImagenDTO>> getAllImagens(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Imagens");
        Page<ImagenDTO> page = imagenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /imagens/:id} : get the "id" imagen.
     *
     * @param id the id of the imagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/imagens/{id}")
    public ResponseEntity<ImagenDTO> getImagen(@PathVariable Long id) {
        log.debug("REST request to get Imagen : {}", id);
        Optional<ImagenDTO> imagenDTO = imagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagenDTO);
    }

    /**
     * {@code DELETE  /imagens/:id} : delete the "id" imagen.
     *
     * @param id the id of the imagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/imagens/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        log.debug("REST request to delete Imagen : {}", id);
        imagenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
