package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PlataformaService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PlataformaDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Plataforma}.
 */
@RestController
@RequestMapping("/api")
public class PlataformaResource {

    private final Logger log = LoggerFactory.getLogger(PlataformaResource.class);

    private static final String ENTITY_NAME = "plataforma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlataformaService plataformaService;

    public PlataformaResource(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    /**
     * {@code POST  /plataformas} : Create a new plataforma.
     *
     * @param plataformaDTO the plataformaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plataformaDTO, or with status {@code 400 (Bad Request)} if the plataforma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plataformas")
    public ResponseEntity<PlataformaDTO> createPlataforma(@RequestBody PlataformaDTO plataformaDTO) throws URISyntaxException {
        log.debug("REST request to save Plataforma : {}", plataformaDTO);
        if (plataformaDTO.getId() != null) {
            throw new BadRequestAlertException("A new plataforma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlataformaDTO result = plataformaService.save(plataformaDTO);
        return ResponseEntity.created(new URI("/api/plataformas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plataformas} : Updates an existing plataforma.
     *
     * @param plataformaDTO the plataformaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plataformaDTO,
     * or with status {@code 400 (Bad Request)} if the plataformaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plataformaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plataformas")
    public ResponseEntity<PlataformaDTO> updatePlataforma(@RequestBody PlataformaDTO plataformaDTO) throws URISyntaxException {
        log.debug("REST request to update Plataforma : {}", plataformaDTO);
        if (plataformaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlataformaDTO result = plataformaService.save(plataformaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plataformaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plataformas} : get all the plataformas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plataformas in body.
     */
    @GetMapping("/plataformas")
    public ResponseEntity<List<PlataformaDTO>> getAllPlataformas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Plataformas");
        Page<PlataformaDTO> page = plataformaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/plataformas/all")
    public ResponseEntity<List<PlataformaDTO>> findAll() {
        log.debug("REST request to get a page of Plataformas");
        List<PlataformaDTO> page = plataformaService.getAll();
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /plataformas/:id} : get the "id" plataforma.
     *
     * @param id the id of the plataformaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plataformaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plataformas/{id}")
    public ResponseEntity<PlataformaDTO> getPlataforma(@PathVariable Long id) {
        log.debug("REST request to get Plataforma : {}", id);
        Optional<PlataformaDTO> plataformaDTO = plataformaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plataformaDTO);
    }

    /**
     * {@code DELETE  /plataformas/:id} : delete the "id" plataforma.
     *
     * @param id the id of the plataformaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plataformas/{id}")
    public ResponseEntity<Void> deletePlataforma(@PathVariable Long id) {
        log.debug("REST request to delete Plataforma : {}", id);
        plataformaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
