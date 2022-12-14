package com.mycompany.myapp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.Cliente;

import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Venta} entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    private LocalDate fechaVenta;

    private Double precioVenta;


    private Cliente cliente;

    private Set<ProductoDTO> productos = new HashSet<>();

    private Set<VideoJuegosDTO> videoJuegos = new HashSet<>();

    private DireccionDTO direccion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoDTO> productos) {
        this.productos = productos;
    }

    public Set<VideoJuegosDTO> getVideoJuegos() {
        return videoJuegos;
    }

    public void setVideoJuegos(Set<VideoJuegosDTO> videoJuegos) {
        this.videoJuegos = videoJuegos;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (ventaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ventaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            ", cliente=" + getCliente() +
            ", direccion=" + getDireccion() +
            "}";
    }
}
