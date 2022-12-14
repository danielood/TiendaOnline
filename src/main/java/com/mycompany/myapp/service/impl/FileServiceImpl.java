package com.mycompany.myapp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final ApplicationProperties applicationProperties;

    public FileServiceImpl(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Override
    public String saveFile(Fichero file) {
        return saveLocalFile(file).getAbsolutePath();
    }

    private File saveLocalFile(Fichero file){
        File directory = new File(String.format("%s%s",applicationProperties.getFilepath(),"VideoJuegos"));
        if(!directory.exists()){
            directory.mkdirs();
        }
        File localFile = new File(directory,String.format("file-%s.%s", new Date().getTime(),FilenameUtils.getExtension(file.getFileName())));
        decodeBase64(file, localFile);
        return localFile;
    }

    private void decodeBase64(Fichero file, File localFile) {
        byte[] data = Base64.getDecoder().decode(file.getFileBase64());
        try (OutputStream out = new FileOutputStream(localFile)) {
            out.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String encodeBase64(File file){
        String base64 = "";
        try{
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            base64 = Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64;
    }

}
