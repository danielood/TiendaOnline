package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.List;

import com.mycompany.myapp.domain.Fichero;

public class ProductoTablaDTO implements Serializable{
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Fichero imagen;
    private List<String> plataformas;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Fichero getImagen() {
        return imagen;
    }
    public void setImagen(Fichero imagen) {
        this.imagen = imagen;
    }
    public List<String> getPlataformas() {
        return plataformas;
    }
    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }

}
