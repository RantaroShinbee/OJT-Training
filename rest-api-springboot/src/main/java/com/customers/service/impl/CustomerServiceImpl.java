package com.customers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.customers.entity.Customer;
import com.customers.repository.CustomerRepository;
import com.customers.repository.CustomerRepositoryCustom;
import com.customers.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerRepositoryCustom customerRepositoryCustom;
	
	@Override
	public List<Customer> findAllByOrderByCreateAt() {
		return customerRepository.findAllByOrderByCreateAt();
	}

	@Override
	public Customer findBy_id(String _id) {
		return customerRepository.findBy_id(_id);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteBy_id(String _id) {
		customerRepository.deleteBy_id(_id);
	}

	@Override
	public List<Customer> findAllByOrderByCreateAt(int offset, int limit) {
		return customerRepository.findAllByOrderByCreateAt(PageRequest.of(offset, limit));
	}

	@Override
	public void update(Customer customer) {
		customerRepositoryCustom.update(customer);
	}

}
