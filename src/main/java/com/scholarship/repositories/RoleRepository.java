package com.scholarship.repositories;

import com.scholarship.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.naming.Name;

public interface RoleRepository extends JpaRepository<Role, String> {
}
