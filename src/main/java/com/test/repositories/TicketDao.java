package com.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.Ticket;
import com.test.entities.User;

@Repository
public interface TicketDao extends CrudRepository<Ticket, Integer>, JpaRepository<Ticket, Integer>{
//	Optional<User> findByUsername(String userName);
}
