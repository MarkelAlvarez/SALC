package com.ahsoka.SALC.user_model.persistance.repository;

import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

	@Query("{\"match_phrase\": {\"email\": \"?0\"}}")
	Optional<User> findByEmail(String email);
	List<User> findAll();
	void deleteByEmail(String email);
}
