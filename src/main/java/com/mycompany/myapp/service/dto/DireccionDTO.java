package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.Cliente;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Direccion} entity.
 */
public class DireccionDTO implements Serializable {

    private Long id;

    private String pais;

    private String provincia;

    private String ciudad;

    private String calle;

    private String portal;

    private String escalera;

    private String piso;

    private String letra;


    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getEscalera() {
        return escalera;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DireccionDTO direccionDTO = (DireccionDTO) o;
        if (direccionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), direccionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DireccionDTO{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", calle='" + getCalle() + "'" +
            ", portal='" + getPortal() + "'" +
            ", escalera='" + getEscalera() + "'" +
            ", piso='" + getPiso() + "'" +
            ", letra='" + getLetra() + "'" +
            ", cliente=" + getCliente() +
            "}";
    }
}
