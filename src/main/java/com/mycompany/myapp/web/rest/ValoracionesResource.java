package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ValoracionesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ValoracionesDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Valoraciones}.
 */
@RestController
@RequestMapping("/api")
public class ValoracionesResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionesResource.class);

    private static final String ENTITY_NAME = "valoraciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValoracionesService valoracionesService;

    public ValoracionesResource(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;
    }

    /**
     * {@code POST  /valoraciones} : Create a new valoraciones.
     *
     * @param valoracionesDTO the valoracionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new valoracionesDTO, or with status {@code 400 (Bad Request)} if the valoraciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/valoraciones")
    public ResponseEntity<ValoracionesDTO> createValoraciones(@RequestBody ValoracionesDTO valoracionesDTO) throws URISyntaxException {
        log.debug("REST request to save Valoraciones : {}", valoracionesDTO);
        if (valoracionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new valoraciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValoracionesDTO result = valoracionesService.save(valoracionesDTO);
        return ResponseEntity.created(new URI("/api/valoraciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /valoraciones} : Updates an existing valoraciones.
     *
     * @param valoracionesDTO the valoracionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated valoracionesDTO,
     * or with status {@code 400 (Bad Request)} if the valoracionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the valoracionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/valoraciones")
    public ResponseEntity<ValoracionesDTO> updateValoraciones(@RequestBody ValoracionesDTO valoracionesDTO) throws URISyntaxException {
        log.debug("REST request to update Valoraciones : {}", valoracionesDTO);
        if (valoracionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ValoracionesDTO result = valoracionesService.save(valoracionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, valoracionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /valoraciones} : get all the valoraciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of valoraciones in body.
     */
    @GetMapping("/valoraciones")
    public ResponseEntity<List<ValoracionesDTO>> getAllValoraciones(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Valoraciones");
        Page<ValoracionesDTO> page = valoracionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /valoraciones/:id} : get the "id" valoraciones.
     *
     * @param id the id of the valoracionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the valoracionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/valoraciones/{id}")
    public ResponseEntity<ValoracionesDTO> getValoraciones(@PathVariable Long id) {
        log.debug("REST request to get Valoraciones : {}", id);
        Optional<ValoracionesDTO> valoracionesDTO = valoracionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(valoracionesDTO);
    }

    /**
     * {@code DELETE  /valoraciones/:id} : delete the "id" valoraciones.
     *
     * @param id the id of the valoracionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/valoraciones/{id}")
    public ResponseEntity<Void> deleteValoraciones(@PathVariable Long id) {
        log.debug("REST request to delete Valoraciones : {}", id);
        valoracionesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
