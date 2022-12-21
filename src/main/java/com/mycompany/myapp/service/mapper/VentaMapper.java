package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ProductoMapper.class, VideoJuegosMapper.class, DireccionMapper.class})
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {

    default Venta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venta venta = new Venta();
        venta.setId(id);
        return venta;
    }
}
