package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CarritoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Carrito} and its DTO {@link CarritoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, VideoJuegosMapper.class, ProductoMapper.class})
public interface CarritoMapper extends EntityMapper<CarritoDTO, Carrito> {

    @Mapping(source = "cliente.id", target = "clienteId")
    CarritoDTO toDto(Carrito carrito);

    @Mapping(source = "clienteId", target = "cliente")
    Carrito toEntity(CarritoDTO carritoDTO);

    default Carrito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrito carrito = new Carrito();
        carrito.setId(id);
        return carrito;
    }
}
