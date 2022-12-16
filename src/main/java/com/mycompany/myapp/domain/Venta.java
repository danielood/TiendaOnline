package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @ManyToOne
    @JsonIgnoreProperties("ventas")
    private Cliente cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "venta_producto",
               joinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "venta_video_juegos",
               joinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "video_juegos_id", referencedColumnName = "id"))
    private Set<VideoJuegos> videoJuegos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public Venta fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public Venta precioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Venta productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Venta addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getVentas().add(this);
        return this;
    }

    public Venta removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getVentas().remove(this);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<VideoJuegos> getVideoJuegos() {
        return videoJuegos;
    }

    public Venta videoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
        return this;
    }

    public Venta addVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.add(videoJuegos);
        videoJuegos.getVentas().add(this);
        return this;
    }

    public Venta removeVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.remove(videoJuegos);
        videoJuegos.getVentas().remove(this);
        return this;
    }

    public void setVideoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            "}";
    }
}
