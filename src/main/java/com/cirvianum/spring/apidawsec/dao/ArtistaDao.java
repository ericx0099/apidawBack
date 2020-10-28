package com.cirvianum.spring.apidawsec.dao;


import com.cirvianum.spring.apidawsec.entities.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaDao extends JpaRepository<Artista, Integer> {
}