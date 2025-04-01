package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppCache appCache;
	
	@GetMapping("/all/users")
	public ResponseEntity<?> getAllUsersList() {
	    List<User> list = userService.getAll();
	    if (list != null && !list.isEmpty()) {
	        return new ResponseEntity<>(list, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/clear-app-cache")
	public void clearAppCache() {
		appCache.init();
		
	}
}
