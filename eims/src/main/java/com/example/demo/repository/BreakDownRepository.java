package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BreakDown;

@Repository
public interface BreakDownRepository extends JpaRepository<BreakDown, Long>{
	
}
