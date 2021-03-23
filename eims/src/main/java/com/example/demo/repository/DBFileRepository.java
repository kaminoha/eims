package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{
	@Query(value = "Select * from files where file_id = '?1'",
			nativeQuery = true)
	DBFile findById(String fileId);
}
