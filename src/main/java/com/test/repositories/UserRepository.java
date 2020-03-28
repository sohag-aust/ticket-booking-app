package com.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer>{
	Optional<User> findByUsername(String userName);
}