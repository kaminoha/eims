package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
/*	@Query(value = "select * from users where username = '?1' and pass = '?2'",
			nativeQuery = true)*/
	List<Users> findByUsernameAndPassword(String username, String pass);
}
