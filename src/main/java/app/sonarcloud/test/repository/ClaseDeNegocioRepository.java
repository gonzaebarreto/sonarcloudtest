package app.sonarcloud.test.repository;

import app.sonarcloud.test.domain.ClaseDeNegocio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClaseDeNegocio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseDeNegocioRepository extends JpaRepository<ClaseDeNegocio, Long> {

}
