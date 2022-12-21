package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Imagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Imagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    @Query("select new com.mycompany.myapp.domain.AuxRepository(v.id, i.path) from VideoJuegos v, Imagen i where i.id = v.caratula.id and v.id in (:ids)")
    List<AuxRepository> findCaratulas(@Param("ids")List<Long> ids);

    @Query("select new com.mycompany.myapp.domain.AuxRepository(p.id, i.path) from Producto p, Imagen i where i.id = p.imagen.id and p.id in (:ids)")
    List<AuxRepository> findImagenes(@Param("ids")List<Long> ids);

    @Query("SELECT i from Imagen i, VideoJuegos v where v.caratula.id = i.id and v.id = :id")
    Optional<Imagen> findImagenFromVideoJuegoId(@Param("id")Long id);

    @Query("SELECT i from Imagen i, Producto p where p.imagen.id = i.id and p.id = :id")
    Optional<Imagen> findImagenFromProductoId(@Param("id")Long id);

}
