package net.engineeringdigest.journalApp.controller;




import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositry.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

	//Controller communicates with service and service communicates with Repository
		@Autowired
		private UserService userService;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private WeatherService weatherService;
		
		
		
		//API to fetch all user
		@GetMapping("/all")
		public ResponseEntity<?> getAllUsers() {
			try {
				List<User> usersList = userService.getAll();
				if(usersList.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				log.info("User Fetched Successfully");
				return new ResponseEntity<>(usersList,HttpStatus.OK);
				
			} catch (Exception e) {
				log.error("Something Went Wrong in Server");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} //end
		
		
		//API for deleting journal entry
		@DeleteMapping("/delete")
		public ResponseEntity<?> deleteUserByUserName() {
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				userRepository.deleteByUserName(authentication.getName());
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			 
		}
		//end
		
		
		//API for updating user information
		@PutMapping("/update")
		public ResponseEntity<?> updateUser(@RequestBody User newUser ){
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String username = authentication.getName();
				User userInDb = userService.findByUserName(username);
				if(userInDb!=null) {
					userInDb.setUserName(newUser.getUserName());
					userInDb.setPassword(newUser.getPassword());
					userService.saveNewUser(userInDb);
					return new ResponseEntity<>(userInDb,HttpStatus.OK);
				}
				return new ResponseEntity<>("User not found!",HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		
		@GetMapping("/greeting")
		public ResponseEntity<?> greetingUser() {
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String username = authentication.getName();
				double weatherResponse =  weatherService.getWether("Pune").getMain().getTemp();
				System.out.println(weatherResponse);
				return new ResponseEntity<>("HI "+username+" Wether Feels Like: "+ weatherResponse  ,HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		    
		
		

}
