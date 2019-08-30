package app.sonarcloud.test.service.mapper;

import app.sonarcloud.test.domain.*;
import app.sonarcloud.test.service.dto.ClaseDeNegocioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClaseDeNegocio and its DTO ClaseDeNegocioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClaseDeNegocioMapper extends EntityMapper<ClaseDeNegocioDTO, ClaseDeNegocio> {



    default ClaseDeNegocio fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClaseDeNegocio claseDeNegocio = new ClaseDeNegocio();
        claseDeNegocio.setId(id);
        return claseDeNegocio;
    }
}
