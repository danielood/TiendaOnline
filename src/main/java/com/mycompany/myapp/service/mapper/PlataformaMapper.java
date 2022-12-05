package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PlataformaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plataforma} and its DTO {@link PlataformaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlataformaMapper extends EntityMapper<PlataformaDTO, Plataforma> {


    @Mapping(target = "videoJuegos", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Plataforma toEntity(PlataformaDTO plataformaDTO);

    default Plataforma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plataforma plataforma = new Plataforma();
        plataforma.setId(id);
        return plataforma;
    }
}
