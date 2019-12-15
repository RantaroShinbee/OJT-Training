package com.customers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customers.business.CustomerBusiness;
import com.customers.entity.Customer;
import com.customers.service.CustomerService;

@RestController
@RequestMapping(value = "api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerBusiness customerBusiness;

	@GetMapping(value = "")
	public ResponseEntity<List<Customer>> findCustomers(
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
		List<Customer> customers;

		if (offset == null && limit == null) {
			customers = customerService.findAllByOrderByCreateAt();
		} else if (offset > 0 && limit > 0) {
			customers = customerService.findAllByOrderByCreateAt(offset, limit);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (customerBusiness.checkListCustomersEmpty(customers)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@GetMapping(value = "/{_id}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable String _id) {
		Customer customer = customerService.findBy_id(_id);
		if (customerBusiness.checkCustomersEmpty(customer)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
		Customer c = customerService.save(customer);
		if (c != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{_id}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable String _id) {
		Customer customer = customerService.findBy_id(_id);
		if (customerBusiness.checkCustomersEmpty(customer)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		customerService.deleteBy_id(_id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/{_id}")
	public ResponseEntity<Void> updateCustomersById(@RequestBody Customer customer, @PathVariable String _id) {
		Customer c = customerService.findBy_id(_id);
		if (customerBusiness.checkCustomersEmpty(c)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		customerService.update(customer);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
