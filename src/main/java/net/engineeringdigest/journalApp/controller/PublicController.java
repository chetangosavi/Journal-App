package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

	
			@Autowired
			private UserService userService;
	
	        //API for creating new Journal Entry
			@PostMapping("/user/create")
			public ResponseEntity<User> createEntry(@RequestBody User userEntry ) {
					
				try {
					userService.saveNewUser(userEntry); 
					return new ResponseEntity<>(userEntry,HttpStatus.CREATED);
					
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			//end
}
