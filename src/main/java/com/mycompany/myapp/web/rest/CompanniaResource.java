package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CompanniaService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CompanniaDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Compannia}.
 */
@RestController
@RequestMapping("/api")
public class CompanniaResource {

    private final Logger log = LoggerFactory.getLogger(CompanniaResource.class);

    private static final String ENTITY_NAME = "compannia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanniaService companniaService;

    public CompanniaResource(CompanniaService companniaService) {
        this.companniaService = companniaService;
    }

    /**
     * {@code POST  /compannias} : Create a new compannia.
     *
     * @param companniaDTO the companniaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companniaDTO, or with status {@code 400 (Bad Request)} if the compannia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compannias")
    public ResponseEntity<CompanniaDTO> createCompannia(@RequestBody CompanniaDTO companniaDTO) throws URISyntaxException {
        log.debug("REST request to save Compannia : {}", companniaDTO);
        if (companniaDTO.getId() != null) {
            throw new BadRequestAlertException("A new compannia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanniaDTO result = companniaService.save(companniaDTO);
        return ResponseEntity.created(new URI("/api/compannias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compannias} : Updates an existing compannia.
     *
     * @param companniaDTO the companniaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companniaDTO,
     * or with status {@code 400 (Bad Request)} if the companniaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companniaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compannias")
    public ResponseEntity<CompanniaDTO> updateCompannia(@RequestBody CompanniaDTO companniaDTO) throws URISyntaxException {
        log.debug("REST request to update Compannia : {}", companniaDTO);
        if (companniaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanniaDTO result = companniaService.save(companniaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companniaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compannias} : get all the compannias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compannias in body.
     */
    @GetMapping("/compannias")
    public ResponseEntity<List<CompanniaDTO>> getAllCompannias(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Compannias");
        Page<CompanniaDTO> page = companniaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/compannias/all")
    public ResponseEntity<List<CompanniaDTO>> findAll() {
        log.debug("REST request to get a page of Compannias");
        List<CompanniaDTO> page = companniaService.getAll();
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /compannias/:id} : get the "id" compannia.
     *
     * @param id the id of the companniaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companniaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compannias/{id}")
    public ResponseEntity<CompanniaDTO> getCompannia(@PathVariable Long id) {
        log.debug("REST request to get Compannia : {}", id);
        Optional<CompanniaDTO> companniaDTO = companniaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companniaDTO);
    }

    /**
     * {@code DELETE  /compannias/:id} : delete the "id" compannia.
     *
     * @param id the id of the companniaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compannias/{id}")
    public ResponseEntity<Void> deleteCompannia(@PathVariable Long id) {
        log.debug("REST request to delete Compannia : {}", id);
        companniaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
