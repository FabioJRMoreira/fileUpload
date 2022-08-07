package com.fabio.fileUpload.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
    private String id;
    private String name;
    private long size;
    private String url;
    private String type;
}
