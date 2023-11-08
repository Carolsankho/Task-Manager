package com.manage.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manage.task.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query(value="select * from roles r where r.role = :role",nativeQuery = true)
    Role findByRole(String role);
}
