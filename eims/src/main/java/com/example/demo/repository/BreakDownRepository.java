package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BreakDown;

@Repository
public interface BreakDownRepository extends JpaRepository<BreakDown, Long>{
	@Query(value = "select * from break_down as b left join finance as f "
			+ "ON b.finance_id = f.finance_id where b.finance_id = :finance_id",
			nativeQuery = true)
	List<BreakDown> findByFinanceId(@Param("financeId") Long financeId);
}
