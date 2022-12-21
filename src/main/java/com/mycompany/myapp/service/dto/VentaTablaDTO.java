package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class VentaTablaDTO implements Serializable {

    private Long id;
    private LocalDate fechaVenta;
    private Double precioVenta;
    private Long clienteId;

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
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
