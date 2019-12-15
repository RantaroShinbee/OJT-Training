package com.customers.business;

import java.util.List;

import org.springframework.stereotype.Component;

import com.customers.entity.Customer;

@Component
public class CustomerBusiness {
	public boolean checkListCustomersEmpty(List<Customer> customer) {
		if (customer.isEmpty()) {
			return true;
		}
		
		return false;
	}

	public boolean checkCustomersEmpty(Customer customer) {
		if (customer == null) {
			return true;
		}
		
		return false;
	}
}
