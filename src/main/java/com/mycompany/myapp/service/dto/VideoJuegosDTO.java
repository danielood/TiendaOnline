package com.mycompany.myapp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.enumeration.Pegi;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.VideoJuegos} entity.
 */
public class VideoJuegosDTO implements Serializable {

    private Long id;
    private String titulo;
    private String sinopsis;
    private Pegi pegi;
    private LocalDate fechaLanzamiento;
    private Double precio;
    private Integer stock;
    private Boolean destacado;
    private Fichero caratula;
    private CompanniaDTO compannia;
    private List<PlataformaDTO> plataformas = new ArrayList<>();
    private List<CategoriaDTO> categorias = new ArrayList<>();
    private Double valoracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public Fichero getCaratula() {
        return caratula;
    }

    public void setCaratula(Fichero caratula) {
        this.caratula = caratula;
    }

    public CompanniaDTO getCompannia() {
        return compannia;
    }

    public void setCompannia(CompanniaDTO compannia) {
        this.compannia = compannia;
    }

    public List<PlataformaDTO> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<PlataformaDTO> plataformas) {
        this.plataformas = plataformas;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VideoJuegosDTO videoJuegosDTO = (VideoJuegosDTO) o;
        if (videoJuegosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), videoJuegosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VideoJuegosDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", sinopsis='" + getSinopsis() + "'" +
            ", pegi='" + getPegi() + "'" +
            ", fechaLanzamiento='" + getFechaLanzamiento() + "'" +
            ", precio=" + getPrecio() +
            ", stock=" + getStock() +
            ", destacado='" + isDestacado() + "'" +
            ", caratula=" + getCaratula() +
            ", compannia=" + getCompannia() +
            "}";
    }
}
