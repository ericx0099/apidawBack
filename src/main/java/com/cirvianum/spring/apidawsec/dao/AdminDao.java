package com.cirvianum.spring.apidawsec.dao;


import com.cirvianum.spring.apidawsec.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin, Integer> {
}
