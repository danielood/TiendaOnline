package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Valoraciones} entity.
 */
public class ValoracionesDTO implements Serializable {

    private Long id;

    private Integer puntuacion;

    private String comentario;


    private Long clienteId;

    private Long videoJuegosId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVideoJuegosId() {
        return videoJuegosId;
    }

    public void setVideoJuegosId(Long videoJuegosId) {
        this.videoJuegosId = videoJuegosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValoracionesDTO valoracionesDTO = (ValoracionesDTO) o;
        if (valoracionesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoracionesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValoracionesDTO{" +
            "id=" + getId() +
            ", puntuacion=" + getPuntuacion() +
            ", comentario='" + getComentario() + "'" +
            ", cliente=" + getClienteId() +
            ", videoJuegos=" + getVideoJuegosId() +
            "}";
    }
}
