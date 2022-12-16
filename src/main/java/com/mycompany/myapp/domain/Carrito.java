package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @JoinTable(name = "carrito_video_juegos",
               joinColumns = @JoinColumn(name = "carrito_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "video_juegos_id", referencedColumnName = "id"))
    private Set<VideoJuegos> videoJuegos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "carrito_producto",
               joinColumns = @JoinColumn(name = "carrito_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

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

    public Set<VideoJuegos> getVideoJuegos() {
        return videoJuegos;
    }

    public Carrito videoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
        return this;
    }

    public void setVideoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Carrito productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
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
