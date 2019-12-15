package com.customers.business;

import java.util.List;

import org.springframework.stereotype.Component;

import com.customers.entity.User;

@Component
public class UserBusiness {

	public boolean checkListUsersEmpty(List<User> users) {
		if (users.isEmpty()) {
			return true;
		}
		
		return false;
	}

	public boolean checkUsersEmpty(User user) {
		if (user == null) {
			return true;
		}
		
		return false;
	}
}
