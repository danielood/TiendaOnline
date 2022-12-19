package com.mycompany.myapp.service;

import java.io.File;

public interface MimeTypeService {

    public String findMimeType(File file);
    public String findMimeType(String fileName);
}
