package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mycompany.myapp.domain.Fichero;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer stock;


    private Fichero imagen;

    private List<PlataformaDTO> plataformas = new ArrayList<>();

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<PlataformaDTO> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<PlataformaDTO> plataformas) {
        this.plataformas = plataformas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", stock=" + getStock() +
            ", imagen=" + getImagen() +
            "}";
    }
}
