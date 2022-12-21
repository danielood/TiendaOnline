package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VideoJuegosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VideoJuegos} and its DTO {@link VideoJuegosDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanniaMapper.class, PlataformaMapper.class, CategoriaMapper.class})
public interface VideoJuegosMapper extends EntityMapper<VideoJuegosDTO, VideoJuegos> {
    default VideoJuegos fromId(Long id) {
        if (id == null) {
            return null;
        }
        VideoJuegos videoJuegos = new VideoJuegos();
        videoJuegos.setId(id);
        return videoJuegos;
    }
}
