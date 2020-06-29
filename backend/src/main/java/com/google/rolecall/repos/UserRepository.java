package com.google.rolecall.repos;

import java.util.Optional;
import java.util.List;

import com.google.rolecall.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByFirstNameAndLastNameAndEmailIgnoreCase(String firstName, String lastName, String email);

  Optional<User> findByEmailIgnoreCase(String email);

  List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
