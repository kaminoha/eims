package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Finance;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long>{
	List<Finance> findTop5ByOrderByRevenueDesc();
	List<Finance> findTop5ByOrderByProfitDesc();
	
	@Query(
			value = "SELECT currency, COUNT(currency) AS tally FROM finance "
					+ "GROUP BY currency ORDER BY tally DESC LIMIT 5",
			nativeQuery=true)
	List<Object[]> findByCurrency();
	@Query(value = "Select * from finance ORDER BY f_date",
			nativeQuery = true)
	List<Finance> findByFdate();
	
	@Query(value = "Select * from finance where finance_id = :financeId",
			nativeQuery = true)
	List<Finance> findByFinanceId(@Param("financeId") Long financeId);
}
