package com.customers.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.customers.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
	
	List<Customer> findAllByOrderByCreateAt();

	Customer findBy_id(String _id);

	void deleteBy_id(String _id);

	List<Customer> findAllByOrderByCreateAt(PageRequest pageRequest);
}
