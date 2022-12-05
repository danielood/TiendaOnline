package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Carrito} entity.
 */
public class CarritoDTO implements Serializable {

    private Long id;


    private Long clienteId;

    private Set<VentaDTO> ventas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Set<VentaDTO> getVentas() {
        return ventas;
    }

    public void setVentas(Set<VentaDTO> ventas) {
        this.ventas = ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoDTO carritoDTO = (CarritoDTO) o;
        if (carritoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarritoDTO{" +
            "id=" + getId() +
            ", cliente=" + getClienteId() +
            "}";
    }
}
