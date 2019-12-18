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
/*
	1. Nên sử dụng int cho offset và limit vì sẽ giảm được độ phức tạp khi xử lý của source code
	2. Không sử dụng HttpStatus.NOT_FOUND cho trường hợp get data từ DB không có dữ liệu
	3. Hạn chế sử dụng case get data vì sẽ ảnh hưởng đến performance của hệ thống
	4. Xử lý if-else trong service
	5. Hàm checkListCustomersEmpty có thể check null hoặc size của mảng (thay vì tạo hàm mới)
	6. Tạo response package map data và return response cho client
	7. Tầng business là để xử lý logic cho API chứ không phải để khai báo hàm validate, các hàm validate nên được common hóa ở dạng tĩnh ở một file common
	8. API path nên được define sao cho common hóa ở dạng static trong một class cụ thể
	9. Nên thêm comment cho các method ở đầu hàm
	10. Nên sử dụng Abstract chung cho các controllers
	11. Nên sử dụng hàm check empty của springframework để check null và empty
	12. Cần thêm config log cho app
	13. Nên thêm xử lý exception cho app
	14. Nên thêm request package map data nhận dữ liệu xử lý
*/
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
	// updateCustomersById -> updateCustomerById
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
