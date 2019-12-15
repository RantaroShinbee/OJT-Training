package com.customers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.customers.business.UserBusiness;
import com.customers.entity.User;
import com.customers.service.JwtService;
import com.customers.service.UserService;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserBusiness userBusiness;

	@GetMapping(value = "")
	public ResponseEntity<List<User>> findUsers(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
		List<User> users;

		if (offset == null && limit == null) {
			users = userService.findAll();
		} else if (offset > 0 && limit > 0) {
			users = userService.findAll(offset, limit);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (userBusiness.checkListUsersEmpty(users)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (userBusiness.checkUsersEmpty(user)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User user) {
		User u = userService.findByEmail(user.getEmail());
		if (userBusiness.checkUsersEmpty(u)) {
			userService.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateUserById(@RequestBody User user, @PathVariable int id) {
		User u = userService.findById(id);
		if (userBusiness.checkUsersEmpty(u)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.update(user);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
		User u = userService.findById(id);
		if (userBusiness.checkUsersEmpty(u)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.deleteById(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User userForm) {
		String result = "";
		HttpStatus httpStatus;
		try {
			boolean checkLogin = userService.checkLogin(userForm);
			if (checkLogin == false) {
				result = "user dont exit !";
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				result = jwtService.generateToken(userForm.getName());
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception ex) {
			result = "error : " + ex.toString();
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(result, httpStatus);
	}

}
