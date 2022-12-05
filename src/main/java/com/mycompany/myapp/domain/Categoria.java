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
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "categorias")
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

    public String getNombre() {
        return nombre;
    }

    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<VideoJuegos> getVideoJuegos() {
        return videoJuegos;
    }

    public Categoria videoJuegos(Set<VideoJuegos> videoJuegos) {
        this.videoJuegos = videoJuegos;
        return this;
    }

    public Categoria addVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.add(videoJuegos);
        videoJuegos.getCategorias().add(this);
        return this;
    }

    public Categoria removeVideoJuegos(VideoJuegos videoJuegos) {
        this.videoJuegos.remove(videoJuegos);
        videoJuegos.getCategorias().remove(this);
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
        if (!(o instanceof Categoria)) {
            return false;
        }
        return id != null && id.equals(((Categoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
