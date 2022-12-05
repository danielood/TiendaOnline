package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Imagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Imagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

}
