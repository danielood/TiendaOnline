package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Compannia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Compannia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanniaRepository extends JpaRepository<Compannia, Long> {

}
