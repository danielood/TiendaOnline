package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plataforma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Plataforma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

}
