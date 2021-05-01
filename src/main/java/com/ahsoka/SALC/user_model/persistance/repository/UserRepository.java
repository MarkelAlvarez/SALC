package com.ahsoka.SALC.user_model.persistance.repository;

import java.util.List;
import java.util.Optional;

import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
	Optional<User> findByEmail(String email);
	List<User> findAll();
}
