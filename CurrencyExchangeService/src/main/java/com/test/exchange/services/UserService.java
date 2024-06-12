package com.test.exchange.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exchange.dto.User;
import com.test.exchange.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User createUser (User user) {		
		return userRepository.save(user);
	}
	
	public User getUserById (Long id) {		
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.get();		
	}
	
	
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }       
}
