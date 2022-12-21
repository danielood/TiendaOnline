package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.IAuxNativeQuery;
import com.mycompany.myapp.domain.Plataforma;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Plataforma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    @Query(value = "select v.id, group_concat(p.nombre) AS concat FROM tiendaonline.video_juegos as v,video_juegos_plataforma as m, plataforma as p where v.id = m.video_juegos_id and p.id = m.plataforma_id and v.id in (:ids) group by v.id;",nativeQuery = true)
    List<IAuxNativeQuery> findAllByVideoJuegoIds(@Param("ids")List<Long> ids);

    @Query(value = "select v.id, group_concat(p.nombre) AS concat FROM tiendaonline.producto as v,producto_plataforma as m, plataforma as p where v.id = m.producto_id and p.id = m.plataforma_id and v.id in (:ids) group by v.id;",nativeQuery = true)
    List<IAuxNativeQuery> findAllByProductoIds(@Param("ids")List<Long> ids);
}
