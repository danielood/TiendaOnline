package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Carrito.
 */
@Entity
@Table(name = "carrito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "carrito_venta",
               joinColumns = @JoinColumn(name = "carrito_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "id"))
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Carrito cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }

    public Carrito ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    public Carrito addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getCarritos().add(this);
        return this;
    }

    public Carrito removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getCarritos().remove(this);
        return this;
    }

    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrito)) {
            return false;
        }
        return id != null && id.equals(((Carrito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Carrito{" +
            "id=" + getId() +
            "}";
    }
}
