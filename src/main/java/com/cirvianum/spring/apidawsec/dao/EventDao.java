package com.cirvianum.spring.apidawsec.dao;



import com.cirvianum.spring.apidawsec.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Integer> {
}