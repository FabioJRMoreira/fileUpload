package com.fabio.fileUpload.service;

import com.fabio.fileUpload.dto.FileDTO;
import com.fabio.fileUpload.model.FileDB;
import com.fabio.fileUpload.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository){
        this.fileRepository=fileRepository;
    }

    public void saveFile(MultipartFile file) throws IOException {
        FileDB fileDB = new FileDB();
        fileDB.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileDB.setType(file.getContentType());
        fileDB.setData(file.getBytes());
        fileDB.setSize(file.getSize());
        fileRepository.save(fileDB);
    }
    public Optional<FileDB> getFile(String id){
        return fileRepository.findById(id);
    }
    public List<FileDB> getList(){
        return fileRepository.findAll();
    }

}
