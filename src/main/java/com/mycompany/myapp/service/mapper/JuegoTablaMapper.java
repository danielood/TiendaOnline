package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.VideoJuegos;
import com.mycompany.myapp.service.dto.JuegoTablaDTO;

import java.util.List;

public class JuegoTablaMapper {

    public JuegoTablaDTO toDTO(VideoJuegos videoJuegos, Fichero caratula, String compannia, List<String> plataformas){
        JuegoTablaDTO juegoTablaDTO = new JuegoTablaDTO();
        juegoTablaDTO.setId(videoJuegos.getId());
        juegoTablaDTO.setTitulo(videoJuegos.getTitulo());
        juegoTablaDTO.setPegi(videoJuegos.getPegi());
        juegoTablaDTO.setCaratula(caratula);
        juegoTablaDTO.setCompannia(compannia);
        juegoTablaDTO.setPlataformas(plataformas);
        return juegoTablaDTO;
    }

    public VideoJuegos toEntity(JuegoTablaDTO juegoTablaDTO){
        //TODO
        return null;
    }
}
