package com.mycompany.myapp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
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


    private Long caratulaId;

    private Long companniaId;

    private Set<ValoracionesDTO> valoraciones = new HashSet<>();

    private Set<PlataformaDTO> plataformas = new HashSet<>();

    private Set<CategoriaDTO> categorias = new HashSet<>();

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

    public Long getCaratulaId() {
        return caratulaId;
    }

    public void setCaratulaId(Long imagenId) {
        this.caratulaId = imagenId;
    }

    public Long getCompanniaId() {
        return companniaId;
    }

    public void setCompanniaId(Long companniaId) {
        this.companniaId = companniaId;
    }

    public Set<ValoracionesDTO> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(Set<ValoracionesDTO> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public Set<PlataformaDTO> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(Set<PlataformaDTO> plataformas) {
        this.plataformas = plataformas;
    }

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaDTO> categorias) {
        this.categorias = categorias;
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
            ", caratula=" + getCaratulaId() +
            ", compannia=" + getCompanniaId() +
            "}";
    }
}
