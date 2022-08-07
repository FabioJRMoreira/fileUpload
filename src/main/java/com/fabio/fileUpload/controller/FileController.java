package com.fabio.fileUpload.controller;

import com.fabio.fileUpload.dto.FileDTO;
import com.fabio.fileUpload.model.FileDB;
import com.fabio.fileUpload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService=fileService;
    }
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Arquivos carregado com sucesso: %s",file.getOriginalFilename()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Nao foi possivel carregar o arquivo: %s",file.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<FileDTO> getListFiles(){
        return  fileService.getList().stream().map(this::convertToFileDTO).collect(Collectors.toList());
    }
    public FileDTO convertToFileDTO(FileDB fileDB){
        String urlFile = ServletUriComponentsBuilder
                         .fromCurrentContextPath()
                         .path("/files/")
                         .path(fileDB.getId())
                         .toUriString();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(fileDB.getId());
        fileDTO.setName(fileDB.getName());
        fileDTO.setType(fileDB.getType());
        fileDTO.setSize(fileDB.getSize());
        fileDTO.setUrl(urlFile);
        return fileDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
        Optional<FileDB> fileDBOptional = fileService.getFile(id);
        if(fileDBOptional.isPresent()){
            FileDB fileDB = fileDBOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"anexo; filename=\""+fileDB.getName()+"")
                    .contentType(MediaType.valueOf(fileDB.getType()))
                    .body(fileDB.getData());
        }
        return ResponseEntity.notFound().build();

    }
}
