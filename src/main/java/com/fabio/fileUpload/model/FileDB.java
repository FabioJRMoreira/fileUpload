package com.fabio.fileUpload.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="files")
@Getter
@Setter
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    private long size;
    @Lob
    private byte[] data;
}
