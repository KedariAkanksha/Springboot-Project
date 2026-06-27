package com.itp.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>  {

}
