package com.fabio.fileUpload.repository;

import com.fabio.fileUpload.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDB,String> {
}
