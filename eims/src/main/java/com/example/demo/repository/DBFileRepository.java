package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Files;

@Repository
public interface DBFileRepository extends JpaRepository<Files, String>{
	@Query(value = "Select * from files where file_id = :fileId",
			nativeQuery = true)
	Files findById(@Param("fileId") String fileId);
	@Query(value = "Select * from files",
			nativeQuery = true)
	List<Files>findFiles();
}
