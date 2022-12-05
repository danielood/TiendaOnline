package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VideoJuegos} and its DTO {@link VideoJuegosDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImagenMapper.class, CompanniaMapper.class, ValoracionesMapper.class, PlataformaMapper.class, CategoriaMapper.class})
public interface VideoJuegosMapper extends EntityMapper<VideoJuegosDTO, VideoJuegos> {

    @Mapping(source = "caratula.id", target = "caratulaId")
    @Mapping(source = "compannia.id", target = "companniaId")
    VideoJuegosDTO toDto(VideoJuegos videoJuegos);

    @Mapping(source = "caratulaId", target = "caratula")
    @Mapping(source = "companniaId", target = "compannia")
    @Mapping(target = "ventas", ignore = true)
    VideoJuegos toEntity(VideoJuegosDTO videoJuegosDTO);

    default VideoJuegos fromId(Long id) {
        if (id == null) {
            return null;
        }
        VideoJuegos videoJuegos = new VideoJuegos();
        videoJuegos.setId(id);
        return videoJuegos;
    }
}
