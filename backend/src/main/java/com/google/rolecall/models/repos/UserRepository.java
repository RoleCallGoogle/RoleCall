package com.google.rolecall.models.repos;

import com.google.rolecall.models.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {}
