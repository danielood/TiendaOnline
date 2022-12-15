package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuxRepository;
import com.mycompany.myapp.domain.VideoJuegos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the VideoJuegos entity.
 */
@Repository
public interface VideoJuegosRepository extends JpaRepository<VideoJuegos, Long> {

    @Query(value = "select distinct videoJuegos from VideoJuegos videoJuegos left join fetch videoJuegos.valoraciones left join fetch videoJuegos.plataformas left join fetch videoJuegos.categorias",
        countQuery = "select count(distinct videoJuegos) from VideoJuegos videoJuegos")
    Page<VideoJuegos> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct videoJuegos from VideoJuegos videoJuegos left join fetch videoJuegos.valoraciones left join fetch videoJuegos.plataformas left join fetch videoJuegos.categorias")
    List<VideoJuegos> findAllWithEagerRelationships();

    @Query("select videoJuegos from VideoJuegos videoJuegos left join fetch videoJuegos.plataformas left join fetch videoJuegos.categorias where videoJuegos.id =:id")
    Optional<VideoJuegos> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select new com.mycompany.myapp.domain.AuxRepository(v.id, c.nombre) from VideoJuegos v, Compannia c where v.id in (:ids) and c.id = v.compannia.id")
    List<AuxRepository> finCompannies(@Param("ids")List<Long> ids);
}
