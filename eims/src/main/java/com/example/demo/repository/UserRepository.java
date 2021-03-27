package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
@Query(value = "select * from users where username = :username and pass = :pass",
			nativeQuery = true)
	List<Users> findByUsernameAndPassword(@Param("username") String username, @Param("pass")String pass);
}
