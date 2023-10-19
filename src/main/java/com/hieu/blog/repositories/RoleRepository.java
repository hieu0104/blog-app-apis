package com.hieu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieu.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>
{

}
