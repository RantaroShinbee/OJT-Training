package com.customers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.customers.business.UserBusiness;
import com.customers.entity.User;
import com.customers.repository.UserRepository;
import com.customers.security.EncrytedPassword;
import com.customers.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserBusiness userBusiness;

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void save(User u) {
		String encrytedPassword = EncrytedPassword.encrytePassword(u.getPassword());
		u.setPassword(encrytedPassword);
		userRepository.save(u);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void update(User user) {
		userRepository.update(user.getName(), user.getEmail(), user.getPassword(), user.getId());
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public boolean checkLogin(User userForm) {
		boolean result;
		User user = findByEmail(userForm.getEmail());
		boolean checkPassword = EncrytedPassword.checkPassword(userForm.getPassword(), user.getPassword());
		if (userBusiness.checkUsersEmpty(user) == true || checkPassword == false) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}

	@Override
	public List<User> findAll(int offset, int limit) {
		return userRepository.findAll(PageRequest.of(offset, limit));
	}

	@Override
	public int count() {
		return ((List<User>) userRepository.findAll()).size();
	}

}
