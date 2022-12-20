package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.service.dto.VentaTablaDTO;

public class VentaTablaMapper {

    public VentaTablaDTO toDTO(Venta venta, Long clienteId){
        VentaTablaDTO ventaTablaDTO = new VentaTablaDTO();
        ventaTablaDTO.setId(venta.getId());
        ventaTablaDTO.setFechaVenta(venta.getFechaVenta());
        ventaTablaDTO.setClienteId(clienteId);
        ventaTablaDTO.setPrecioVenta(venta.getPrecioVenta());
        return ventaTablaDTO;
    }

}
