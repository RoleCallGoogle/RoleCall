package com.google.rolecall.repos;

import com.google.rolecall.models.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {}
