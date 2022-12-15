package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Imagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Imagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    @Query("select new com.mycompany.myapp.domain.AuxRepository(v.id, i.path) from VideoJuegos v, Imagen i where i.id = v.caratula.id and v.id in (:ids)")
    List<AuxRepository> findCaratulas(@Param("ids")List<Long> ids);

}
