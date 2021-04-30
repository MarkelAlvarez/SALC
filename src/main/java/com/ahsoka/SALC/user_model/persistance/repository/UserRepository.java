package com.ahsoka.SALC.user_model.persistance.repository;

@Repository
public interface UserRepository {
	public User findByEmail(String email);
	public List<User> findAll();
}
