package com.ahsoka.SALC.user_model.persistance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahsoka.SALC.user_model.persistance.entity.User;

@Repository
public interface UserRepository {
	public User findByEmail(String email);
	public List<User> findAll();
}
