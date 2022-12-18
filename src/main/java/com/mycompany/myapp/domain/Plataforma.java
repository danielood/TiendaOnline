package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Plataforma.
 */
@Entity
@Table(name = "plataforma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plataforma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "plataformas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<VideoJuegos> videoJuegos = new HashSet<>();

    @ManyToMany(mappedBy = "plataformas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

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

    public Plataforma nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<VideoJuegos> getVideoJuegos() {
        return videoJuegos;
    }

    public Plataforma videoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
        return this;
    }

    public Plataforma addVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.add(videoJuegos);
        videoJuegos.getPlataformas().add(this);
        return this;
    }

    public Plataforma removeVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.remove(videoJuegos);
        videoJuegos.getPlataformas().remove(this);
        return this;
    }

    public void setVideoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Plataforma productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Plataforma addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getPlataformas().add(this);
        return this;
    }

    public Plataforma removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getPlataformas().remove(this);
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
        if (!(o instanceof Plataforma)) {
            return false;
        }
        return id != null && id.equals(((Plataforma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plataforma{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
