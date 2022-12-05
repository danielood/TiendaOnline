package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Imagen} entity.
 */
public class ImagenDTO implements Serializable {

    private Long id;

    private String path;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImagenDTO imagenDTO = (ImagenDTO) o;
        if (imagenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagenDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
