package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Valoraciones.
 */
@Entity
@Table(name = "valoraciones")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Valoraciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne
    @JsonIgnoreProperties("valoraciones")
    private Cliente cliente;

    @ManyToMany(mappedBy = "valoraciones")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<VideoJuegos> videoJuegos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public Valoraciones puntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Valoraciones comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Valoraciones cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<VideoJuegos> getVideoJuegos() {
        return videoJuegos;
    }

    public Valoraciones videoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
        return this;
    }

    public Valoraciones addVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.add(videoJuegos);
        videoJuegos.getValoraciones().add(this);
        return this;
    }

    public Valoraciones removeVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.remove(videoJuegos);
        videoJuegos.getValoraciones().remove(this);
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
        if (!(o instanceof Valoraciones)) {
            return false;
        }
        return id != null && id.equals(((Valoraciones) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Valoraciones{" +
            "id=" + getId() +
            ", puntuacion=" + getPuntuacion() +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
