package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Direccion;
import com.mycompany.myapp.service.dto.DireccionDTO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Direccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @Query("select d from Direccion d where d.cliente.id =:id")
    List<Direccion> findByClientId(@Param("id") Long id);

}
