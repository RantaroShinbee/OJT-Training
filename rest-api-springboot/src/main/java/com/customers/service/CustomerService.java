package com.customers.service;

import java.util.List;

import com.customers.entity.Customer;

public interface CustomerService {
	List<Customer> findAllByOrderByCreateAt();

	Customer findBy_id(String _id);

	Customer save(Customer customer);
	
	void deleteBy_id(String _id);

	List<Customer> findAllByOrderByCreateAt(int offset, int limit);

	void update(Customer customer);
}
