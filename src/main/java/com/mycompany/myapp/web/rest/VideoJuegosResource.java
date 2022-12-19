package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.service.VideoJuegosService;
import com.mycompany.myapp.service.dto.JuegoTablaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.VideoJuegos}.
 */
@RestController
@RequestMapping("/api")
public class VideoJuegosResource {

    private final Logger log = LoggerFactory.getLogger(VideoJuegosResource.class);

    private static final String ENTITY_NAME = "videoJuegos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoJuegosService videoJuegosService;

    public VideoJuegosResource(VideoJuegosService videoJuegosService) {
        this.videoJuegosService = videoJuegosService;
    }

    @GetMapping("/video-juegos/all")
    public ResponseEntity<List<VideoJuegos>> getAllVideoJuegos() {
        log.debug("REST request to get a page of VideoJuegos");
        List<VideoJuegos> lista = videoJuegosService.findAll2();


        return ResponseEntity.ok().body(lista);
    }

    /**
     * {@code POST  /video-juegos} : Create a new videoJuegos.
     *
     * @param videoJuegosDTO the videoJuegosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoJuegosDTO, or with status {@code 400 (Bad Request)} if the videoJuegos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/video-juegos")
    public ResponseEntity<VideoJuegosDTO> createVideoJuegos(@RequestBody VideoJuegosDTO videoJuegosDTO) throws URISyntaxException {
        log.debug("REST request to save VideoJuegos : {}", videoJuegosDTO);
        if (videoJuegosDTO.getId() != null) {
            throw new BadRequestAlertException("A new videoJuegos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoJuegosDTO result = videoJuegosService.save(videoJuegosDTO);
        return ResponseEntity.created(new URI("/api/video-juegos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-juegos} : Updates an existing videoJuegos.
     *
     * @param videoJuegosDTO the videoJuegosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoJuegosDTO,
     * or with status {@code 400 (Bad Request)} if the videoJuegosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoJuegosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/video-juegos")
    public ResponseEntity<VideoJuegosDTO> updateVideoJuegos(@RequestBody VideoJuegosDTO videoJuegosDTO) throws URISyntaxException {
        log.debug("REST request to update VideoJuegos : {}", videoJuegosDTO);
        if (videoJuegosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoJuegosDTO result = videoJuegosService.save(videoJuegosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoJuegosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /video-juegos} : get all the videoJuegos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoJuegos in body.
     */
    @GetMapping("/video-juegos")
    public ResponseEntity<List<JuegoTablaDTO>> getAllVideoJuegos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of VideoJuegos");
        Page<JuegoTablaDTO> page;
        if (eagerload) {
            page = videoJuegosService.findAllWithEagerRelationships(pageable);
        } else {
            page = videoJuegosService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /video-juegos/:id} : get the "id" videoJuegos.
     *
     * @param id the id of the videoJuegosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoJuegosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-juegos/{id}")
    public ResponseEntity<VideoJuegosDTO> getVideoJuegos(@PathVariable Long id) {
        log.debug("REST request to get VideoJuegos : {}", id);
        Optional<VideoJuegosDTO> videoJuegosDTO = videoJuegosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoJuegosDTO);
    }

    /**
     * {@code DELETE  /video-juegos/:id} : delete the "id" videoJuegos.
     *
     * @param id the id of the videoJuegosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-juegos/{id}")
    public ResponseEntity<Void> deleteVideoJuegos(@PathVariable Long id) {
        log.debug("REST request to delete VideoJuegos : {}", id);
        videoJuegosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
