package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.service.dto.ImagenDTO;

public interface FileService {

    public String saveFile(Fichero file);

    public Fichero getFicheroFromImagen(String path);

    public void deleteFile(String path);
}
