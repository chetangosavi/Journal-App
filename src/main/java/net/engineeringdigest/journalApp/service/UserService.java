package net.engineeringdigest.journalApp.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositry.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	//create user
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public void saveNewUser(User user) {
		 user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
		 user.setRole(Arrays.asList("USER"));
		 userRepository.save(user);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> getById(String id) {
		return userRepository.findById(id); 
	}
	
	public void deleteById(String id){
		userRepository.deleteById(id);
	}
	
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}
}
