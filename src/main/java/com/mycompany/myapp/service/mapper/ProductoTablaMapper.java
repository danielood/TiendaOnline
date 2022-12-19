package com.mycompany.myapp.service.mapper;

import java.util.List;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.service.dto.PlataformaDTO;
import com.mycompany.myapp.service.dto.ProductoTablaDTO;

public class ProductoTablaMapper {

    public ProductoTablaDTO toDTO(Producto producto,Fichero imagen, List<String> plataformas){

        ProductoTablaDTO productoTablaDTO = new ProductoTablaDTO();
        productoTablaDTO.setId(producto.getId());
        productoTablaDTO.setNombre(producto.getNombre());
        productoTablaDTO.setPrecio(producto.getPrecio());
        productoTablaDTO.setStock(producto.getStock());
        productoTablaDTO.setImagen(imagen);
        productoTablaDTO.setPlataformas(plataformas);
        return productoTablaDTO;
    }
}
