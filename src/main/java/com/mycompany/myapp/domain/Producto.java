package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @OneToOne
    @JoinColumn(unique = true)
    private Imagen imagen;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_plataforma",
               joinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "plataforma_id", referencedColumnName = "id"))
    private Set<Plataforma> plataformas = new HashSet<>();

    @ManyToMany(mappedBy = "productos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Producto precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Producto stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public Producto imagen(Imagen imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Set<Plataforma> getPlataformas() {
        return plataformas;
    }

    public Producto plataformas(Set<Plataforma> plataformas) {
        this.plataformas = plataformas;
        return this;
    }

    public Producto addPlataforma(Plataforma plataforma) {
        this.plataformas.add(plataforma);
        plataforma.getProductos().add(this);
        return this;
    }

    public Producto removePlataforma(Plataforma plataforma) {
        this.plataformas.remove(plataforma);
        plataforma.getProductos().remove(this);
        return this;
    }

    public void setPlataformas(Set<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }

    public Producto ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    public Producto addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getProductos().add(this);
        return this;
    }

    public Producto removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getProductos().remove(this);
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
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", stock=" + getStock() +
            "}";
    }
}
