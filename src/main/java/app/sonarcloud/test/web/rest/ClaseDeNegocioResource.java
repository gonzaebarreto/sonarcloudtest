package app.sonarcloud.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.sonarcloud.test.service.ClaseDeNegocioService;
import app.sonarcloud.test.web.rest.errors.BadRequestAlertException;
import app.sonarcloud.test.web.rest.util.HeaderUtil;
import app.sonarcloud.test.service.dto.ClaseDeNegocioDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClaseDeNegocio.
 */
@RestController
@RequestMapping("/api")
public class ClaseDeNegocioResource {

    private final Logger log = LoggerFactory.getLogger(ClaseDeNegocioResource.class);

    private static final String ENTITY_NAME = "sonarcloudtestClaseDeNegocio";

    private ClaseDeNegocioService claseDeNegocioService;

    public ClaseDeNegocioResource(ClaseDeNegocioService claseDeNegocioService) {
        this.claseDeNegocioService = claseDeNegocioService;
    }

    /**
     * POST  /clase-de-negocios : Create a new claseDeNegocio.
     *
     * @param claseDeNegocioDTO the claseDeNegocioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new claseDeNegocioDTO, or with status 400 (Bad Request) if the claseDeNegocio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clase-de-negocios")
    @Timed
    public ResponseEntity<ClaseDeNegocioDTO> createClaseDeNegocio(@RequestBody ClaseDeNegocioDTO claseDeNegocioDTO) throws URISyntaxException {
        log.debug("REST request to save ClaseDeNegocio : {}", claseDeNegocioDTO);
        if (claseDeNegocioDTO.getId() != null) {
            throw new BadRequestAlertException("A new claseDeNegocio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaseDeNegocioDTO result = claseDeNegocioService.save(claseDeNegocioDTO);
        return ResponseEntity.created(new URI("/api/clase-de-negocios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clase-de-negocios : Updates an existing claseDeNegocio.
     *
     * @param claseDeNegocioDTO the claseDeNegocioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated claseDeNegocioDTO,
     * or with status 400 (Bad Request) if the claseDeNegocioDTO is not valid,
     * or with status 500 (Internal Server Error) if the claseDeNegocioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clase-de-negocios")
    @Timed
    public ResponseEntity<ClaseDeNegocioDTO> updateClaseDeNegocio(@RequestBody ClaseDeNegocioDTO claseDeNegocioDTO) throws URISyntaxException {
        log.debug("REST request to update ClaseDeNegocio : {}", claseDeNegocioDTO);
        if (claseDeNegocioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClaseDeNegocioDTO result = claseDeNegocioService.save(claseDeNegocioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, claseDeNegocioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clase-de-negocios : get all the claseDeNegocios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of claseDeNegocios in body
     */
    @GetMapping("/clase-de-negocios")
    @Timed
    public List<ClaseDeNegocioDTO> getAllClaseDeNegocios() {
        log.debug("REST request to get all ClaseDeNegocios");
        return claseDeNegocioService.findAll();
    }

    /**
     * GET  /clase-de-negocios/:id : get the "id" claseDeNegocio.
     *
     * @param id the id of the claseDeNegocioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the claseDeNegocioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clase-de-negocios/{id}")
    @Timed
    public ResponseEntity<ClaseDeNegocioDTO> getClaseDeNegocio(@PathVariable Long id) {
        log.debug("REST request to get ClaseDeNegocio : {}", id);
        Optional<ClaseDeNegocioDTO> claseDeNegocioDTO = claseDeNegocioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claseDeNegocioDTO);
    }

    /**
     * DELETE  /clase-de-negocios/:id : delete the "id" claseDeNegocio.
     *
     * @param id the id of the claseDeNegocioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clase-de-negocios/{id}")
    @Timed
    public ResponseEntity<Void> deleteClaseDeNegocio(@PathVariable Long id) {
        log.debug("REST request to delete ClaseDeNegocio : {}", id);
        claseDeNegocioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
