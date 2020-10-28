package com.cirvianum.spring.apidawsec.dao;


import com.cirvianum.spring.apidawsec.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<Client, Integer> {
}
