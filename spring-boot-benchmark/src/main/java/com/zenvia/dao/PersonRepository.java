package com.zenvia.dao;

import com.zenvia.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {

	Person findByName(String name);

	void deleteByName(String name);

	void save(List<Person> person);
}
