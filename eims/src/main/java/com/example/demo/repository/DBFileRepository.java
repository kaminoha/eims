package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Files;

@Repository
public interface DBFileRepository extends JpaRepository<Files, String>{
	@Query(value = "Select * from files where file_id = '?1'",
			nativeQuery = true)
	Files findById(String fileId);
}
