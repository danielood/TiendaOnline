package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Valoraciones;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Valoraciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionesRepository extends JpaRepository<Valoraciones, Long> {
    @Query("select AVG(va.puntuacion) from Valoraciones va where va.videoJuegos.id = :id")
    Double findAverageFromVideoJuegos(@Param("id") Long id);
}
