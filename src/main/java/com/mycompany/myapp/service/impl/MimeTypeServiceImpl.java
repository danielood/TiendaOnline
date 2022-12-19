package com.mycompany.myapp.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.Files;
import com.mycompany.myapp.service.MimeTypeService;
import com.mycompany.myapp.service.util.MimeType;

@Service
@Transactional
public class MimeTypeServiceImpl implements MimeTypeService {

    @Override
    public String findMimeType(File file) {
        return getMimeType(Files.getFileExtension(file.getName()));
    }

    @Override
    public String findMimeType(String fileName) {
        return getMimeType(Files.getFileExtension(fileName));
    }

    private String getMimeType(String ext){
        switch (ext) {
            case "avif": return MimeType.AVIF;
            case "bmp": return MimeType.BMP;
            case "gif": return MimeType.GIF;
            case "ico": return MimeType.ICO;
            case "jpeg":
            case "jpg": return MimeType.JPEG;
            case "png": return MimeType.PNG;
            case "svg": return MimeType.SVG;
            case "tif":
            case "tiff": return MimeType.TIF;
            case "webp": return MimeType.WEBP;
            default: return "";
        }
    }
}
