package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JWTUtil;

@RestController
@RequestMapping("/public")
public class PublicController {

	
			@Autowired
			private UserService userService;
			
			@Autowired
			private AuthenticationManager authenticationManager;
			
			@Autowired
			private UserDetailsServiceImpl userDetailsServiceImpl;
			
			@Autowired
			private JWTUtil jwtUtil;
			
			//Health Check
			@GetMapping("/health")
			public String healthString() {
				return "I am Ok";
			}
	
	        //API for Sign up User
			@PostMapping("/signup")
			public ResponseEntity<User> signup(@RequestBody User userEntry ) {
					
				try {
					userService.saveNewUser(userEntry); 
					return new ResponseEntity<>(userEntry,HttpStatus.CREATED);
					
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			//end
			
			//API For Login User
			@PostMapping("/login")
			public ResponseEntity<String> login(@RequestBody User user) {
				try {
					authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
					UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
					String jwtTokenString = jwtUtil.generateToken(userDetails.getUsername());
					return new  ResponseEntity<>(jwtTokenString,HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<String>("Invalid Username or Password", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}
}
