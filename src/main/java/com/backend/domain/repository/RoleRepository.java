package com.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
