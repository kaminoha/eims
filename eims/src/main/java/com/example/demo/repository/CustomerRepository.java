package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findTop10ByOrderByFinance_ProfitDesc();
	List<Customer> findTop5ByOrderByFinance_RevenueDesc();
	@Query(
			value = "SELECT customer_address, COUNT(*) AS tally FROM customer GROUP BY customer_address "
					+ "ORDER BY tally DESC LIMIT 5",
					nativeQuery=true)
	List<Object[]> findByAddress();
	
	@Query(
			value = "SELECT COUNT(*) AS tally FROM customer "
					+ "GROUP BY customer_address ORDER BY tally DESC LIMIT 5",
					nativeQuery=true)
	List<?> findByTally();
/*	@Query(
			value = "SELECT MONTH(f_date) as pmonth, SUM(profit) as p from finance "
					+ "where MONTH(f_date) = ?1",
			nativeQuery = true)*/
	@Query(value = "SELECT EXTRACT(MONTH from f_date), SUM(profit) "
			+ "from finance GROUP BY EXTRACT(MONTH from f_date);",
			nativeQuery = true)
	List<Object[]> findByProfit();
	
}
