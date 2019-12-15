package com.customers.service;

import java.util.List;

import com.customers.entity.User;

public interface UserService {

	List<User> findAll();

	List<User> findAll(int offset, int limit);

	User findByName(String username);

	User findById(int id);

	void save(User user);

	User findByEmail(String email);

	void update(User user);

	void deleteById(int id);

	boolean checkLogin(User user);
	
	public int count();
}
