package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Carrito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Carrito entity.
 */
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    @Query(value = "select distinct carrito from Carrito carrito left join fetch carrito.videoJuegos left join fetch carrito.productos",
        countQuery = "select count(distinct carrito) from Carrito carrito")
    Page<Carrito> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct carrito from Carrito carrito left join fetch carrito.videoJuegos left join fetch carrito.productos")
    List<Carrito> findAllWithEagerRelationships();

    @Query("select carrito from Carrito carrito left join fetch carrito.videoJuegos left join fetch carrito.productos where carrito.id =:id")
    Optional<Carrito> findOneWithEagerRelationships(@Param("id") Long id);

}
