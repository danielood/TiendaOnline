package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ValoracionesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Valoraciones} and its DTO {@link ValoracionesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface ValoracionesMapper extends EntityMapper<ValoracionesDTO, Valoraciones> {

    @Mapping(source = "cliente.id", target = "clienteId")
    ValoracionesDTO toDto(Valoraciones valoraciones);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(target = "videoJuegos", ignore = true)
    Valoraciones toEntity(ValoracionesDTO valoracionesDTO);

    default Valoraciones fromId(Long id) {
        if (id == null) {
            return null;
        }
        Valoraciones valoraciones = new Valoraciones();
        valoraciones.setId(id);
        return valoraciones;
    }
}
