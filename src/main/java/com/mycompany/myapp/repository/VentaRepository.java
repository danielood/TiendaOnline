package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Venta entity.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query(value = "select distinct venta from Venta venta left join fetch venta.productos left join fetch venta.videoJuegos",
        countQuery = "select count(distinct venta) from Venta venta")
    Page<Venta> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct venta from Venta venta left join fetch venta.productos left join fetch venta.videoJuegos")
    List<Venta> findAllWithEagerRelationships();

    @Query("select venta from Venta venta left join fetch venta.productos left join fetch venta.videoJuegos where venta.id =:id")
    Optional<Venta> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select new com.mycompany.myapp.domain.AuxRepository(v.id,c.id) from Venta v, Cliente c where v.cliente.id = c.id and v.id in (:ids)")
    List<AuxRepository> findAllClientes(@Param("ids")List<Long> ids);
}
