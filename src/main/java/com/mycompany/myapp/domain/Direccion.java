package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Direccion.
 */
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "calle")
    private String calle;

    @Column(name = "portal")
    private String portal;

    @Column(name = "escalera")
    private String escalera;

    @Column(name = "piso")
    private String piso;

    @Column(name = "letra")
    private String letra;

    @ManyToOne
    @JsonIgnoreProperties("direccions")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public Direccion pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public Direccion provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Direccion ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public Direccion calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPortal() {
        return portal;
    }

    public Direccion portal(String portal) {
        this.portal = portal;
        return this;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getEscalera() {
        return escalera;
    }

    public Direccion escalera(String escalera) {
        this.escalera = escalera;
        return this;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getPiso() {
        return piso;
    }

    public Direccion piso(String piso) {
        this.piso = piso;
        return this;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getLetra() {
        return letra;
    }

    public Direccion letra(String letra) {
        this.letra = letra;
        return this;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Direccion cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion)) {
            return false;
        }
        return id != null && id.equals(((Direccion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", calle='" + getCalle() + "'" +
            ", portal='" + getPortal() + "'" +
            ", escalera='" + getEscalera() + "'" +
            ", piso='" + getPiso() + "'" +
            ", letra='" + getLetra() + "'" +
            "}";
    }
}
