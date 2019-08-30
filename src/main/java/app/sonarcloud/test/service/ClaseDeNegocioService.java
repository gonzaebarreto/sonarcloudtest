package app.sonarcloud.test.service;

import app.sonarcloud.test.domain.ClaseDeNegocio;
import app.sonarcloud.test.repository.ClaseDeNegocioRepository;
import app.sonarcloud.test.service.dto.ClaseDeNegocioDTO;
import app.sonarcloud.test.service.mapper.ClaseDeNegocioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ClaseDeNegocio.
 */
@Service
@Transactional
public class ClaseDeNegocioService {

    private final Logger log = LoggerFactory.getLogger(ClaseDeNegocioService.class);

    private ClaseDeNegocioRepository claseDeNegocioRepository;

    private ClaseDeNegocioMapper claseDeNegocioMapper;

    public ClaseDeNegocioService(ClaseDeNegocioRepository claseDeNegocioRepository, ClaseDeNegocioMapper claseDeNegocioMapper) {
        this.claseDeNegocioRepository = claseDeNegocioRepository;
        this.claseDeNegocioMapper = claseDeNegocioMapper;
    }

    /**
     * Save a claseDeNegocio.
     *
     * @param claseDeNegocioDTO the entity to save
     * @return the persisted entity
     */
    public ClaseDeNegocioDTO save(ClaseDeNegocioDTO claseDeNegocioDTO) {
        log.debug("Request to save ClaseDeNegocio : {}", claseDeNegocioDTO);

        ClaseDeNegocio claseDeNegocio = claseDeNegocioMapper.toEntity(claseDeNegocioDTO);
        claseDeNegocio = claseDeNegocioRepository.save(claseDeNegocio);
        return claseDeNegocioMapper.toDto(claseDeNegocio);
    }

    /**
     * Get all the claseDeNegocios.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClaseDeNegocioDTO> findAll() {
        log.debug("Request to get all ClaseDeNegocios");
        return claseDeNegocioRepository.findAll().stream()
            .map(claseDeNegocioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one claseDeNegocio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ClaseDeNegocioDTO> findOne(Long id) {
        log.debug("Request to get ClaseDeNegocio : {}", id);
        return claseDeNegocioRepository.findById(id)
            .map(claseDeNegocioMapper::toDto);
    }

    /**
     * Delete the claseDeNegocio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaseDeNegocio : {}", id);
        claseDeNegocioRepository.deleteById(id);
    }
}
