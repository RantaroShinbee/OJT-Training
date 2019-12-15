package com.customers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.customers.entity.User;
import com.customers.repository.UserRepository;
import com.customers.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userService;
	@Mock
	UserRepository userRepository;

	@Test
	public void testSavingUsrers() {
		User user = new User("hien", "hien@gmail.com", "123456");
		userService.save(user);

		verify(userRepository).save(user);

		verify(userRepository, times(1)).save(user);
	}

}
