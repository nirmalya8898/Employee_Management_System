package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.emp.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{
	
	@Query("select m from Manager m where m.managerEmail = :email")
	public Manager getManagerByManagerName(@Param("email") String email);
}
