package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Valoraciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Valoraciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionesRepository extends JpaRepository<Valoraciones, Long> {

}
