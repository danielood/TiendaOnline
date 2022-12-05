package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ImagenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Imagen} and its DTO {@link ImagenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagenMapper extends EntityMapper<ImagenDTO, Imagen> {



    default Imagen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Imagen imagen = new Imagen();
        imagen.setId(id);
        return imagen;
    }
}
