package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.Pegi;

/**
 * A VideoJuegos.
 */
@Entity
@Table(name = "video_juegos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VideoJuegos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Enumerated(EnumType.STRING)
    @Column(name = "pegi")
    private Pegi pegi;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "destacado")
    private Boolean destacado;

    @OneToOne
    @JoinColumn(unique = true)
    private Imagen caratula;

    @ManyToOne
    @JsonIgnoreProperties("videoJuegos")
    private Compannia compannia;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "video_juegos_plataforma",
               joinColumns = @JoinColumn(name = "video_juegos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "plataforma_id", referencedColumnName = "id"))
    private Set<Plataforma> plataformas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "video_juegos_categoria",
               joinColumns = @JoinColumn(name = "video_juegos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "videoJuegos")
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

    public String getTitulo() {
        return titulo;
    }

    public VideoJuegos titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public VideoJuegos sinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
        return this;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public VideoJuegos pegi(Pegi pegi) {
        this.pegi = pegi;
        return this;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public VideoJuegos fechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
        return this;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public VideoJuegos precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public VideoJuegos stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean isDestacado() {
        return destacado;
    }

    public VideoJuegos destacado(Boolean destacado) {
        this.destacado = destacado;
        return this;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public Imagen getCaratula() {
        return caratula;
    }

    public VideoJuegos caratula(Imagen imagen) {
        this.caratula = imagen;
        return this;
    }

    public void setCaratula(Imagen imagen) {
        this.caratula = imagen;
    }

    public Compannia getCompannia() {
        return compannia;
    }

    public VideoJuegos compannia(Compannia compannia) {
        this.compannia = compannia;
        return this;
    }

    public void setCompannia(Compannia compannia) {
        this.compannia = compannia;
    }

    public Set<Plataforma> getPlataformas() {
        return plataformas;
    }

    public VideoJuegos plataformas(Set<Plataforma> plataformas) {
        this.plataformas = plataformas;
        return this;
    }

    public VideoJuegos addPlataforma(Plataforma plataforma) {
        this.plataformas.add(plataforma);
        plataforma.getVideoJuegos().add(this);
        return this;
    }

    public VideoJuegos removePlataforma(Plataforma plataforma) {
        this.plataformas.remove(plataforma);
        plataforma.getVideoJuegos().remove(this);
        return this;
    }

    public void setPlataformas(Set<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public VideoJuegos categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public VideoJuegos addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.getVideoJuegos().add(this);
        return this;
    }

    public VideoJuegos removeCategoria(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.getVideoJuegos().remove(this);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }

    public VideoJuegos ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    public VideoJuegos addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.getVideoJuegos().add(this);
        return this;
    }

    public VideoJuegos removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.getVideoJuegos().remove(this);
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
        if (!(o instanceof VideoJuegos)) {
            return false;
        }
        return id != null && id.equals(((VideoJuegos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VideoJuegos{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", sinopsis='" + getSinopsis() + "'" +
            ", pegi='" + getPegi() + "'" +
            ", fechaLanzamiento='" + getFechaLanzamiento() + "'" +
            ", precio=" + getPrecio() +
            ", stock=" + getStock() +
            ", destacado='" + isDestacado() + "'" +
            "}";
    }
}
