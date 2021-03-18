package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>{
	List<Agent> findTop5ByOrderBySalesDesc();
}
