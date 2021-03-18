package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{
	DBFile findById(String fileId);
}
