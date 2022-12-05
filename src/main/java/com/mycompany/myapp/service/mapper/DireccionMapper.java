package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DireccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Direccion} and its DTO {@link DireccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface DireccionMapper extends EntityMapper<DireccionDTO, Direccion> {

    @Mapping(source = "cliente.id", target = "clienteId")
    DireccionDTO toDto(Direccion direccion);

    @Mapping(source = "clienteId", target = "cliente")
    Direccion toEntity(DireccionDTO direccionDTO);

    default Direccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Direccion direccion = new Direccion();
        direccion.setId(id);
        return direccion;
    }
}
