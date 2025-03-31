package net.engineeringdigest.journalApp.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/api/journal")
@Validated
public class JournalEntryController {
	
	//Controller communicates with service and service communicates with Repository
	@Autowired
	private JournalEntryService jEService;
	
	@Autowired
	private UserService  userService;
	
	
	//API for creating new Journal Entry
	@PostMapping("/create")
	public ResponseEntity<JournalEntry> createEntry(@Valid @RequestBody JournalEntry journalEntry ) {
			
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			jEService.saveEntry(journalEntry, username);
			return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//API for fetching single journal based on ID
	@GetMapping("/single/{id}")
	public ResponseEntity<JournalEntry> getJournalById(@PathVariable String id) {
	
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User user = userService.findByUserName(username);
			
			List<JournalEntry> collectEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
			
			if(!collectEntries.isEmpty()) {
				Optional<JournalEntry> newEntry = jEService.getById(id);
				if(newEntry.isPresent()) {
					return new ResponseEntity<JournalEntry>(newEntry.get() , HttpStatus.ACCEPTED); 
				}
			}
			return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND); 	
			
		} catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
			
	}
	
	//API for fetching all journal entries:
	@GetMapping("/all/journals")
	public ResponseEntity<?> getAllJournalEntriesOfUser() {
	    try {
	        
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        
	        User user = userService.findByUserName(username);
	        List<JournalEntry> userEntries = user.getJournalEntries(); // Fetch only this user's entries

	        if (userEntries != null && !userEntries.isEmpty()) {
	            return new ResponseEntity<>(userEntries, HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	//API for deleting journal entry
	@DeleteMapping("/delete/{id}")
	@Transactional
	public ResponseEntity<?> deleteJournalById(@PathVariable String id) {
		try {

			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			 String username = authentication.getName();
			 
			 User user = userService.findByUserName(username);
			 boolean isRemoved = user.getJournalEntries().removeIf(x->x.getId().equals(id));
			 if(isRemoved) {
				 jEService.deleteById(id,username);
				 return new ResponseEntity<>(HttpStatus.NO_CONTENT);	 
			 }
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//API for updating journal entry
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateJournalById(@PathVariable String id , @RequestBody JournalEntry newJournalEntry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			 String username = authentication.getName();
			 
			 User user = userService.findByUserName(username);
			 List<JournalEntry> collectEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

			 if(!collectEntries.isEmpty()) {
				    JournalEntry oldEntry = jEService.getById(id).orElse(null);
					if(oldEntry != null) {
						oldEntry.setTitle(newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()? newJournalEntry.getTitle():oldEntry.getTitle());
						oldEntry.setDescription(newJournalEntry.getDescription() != null && !newJournalEntry.getDescription().isEmpty()? newJournalEntry.getDescription():oldEntry.getDescription());
						jEService.saveEntry(oldEntry);
						return new ResponseEntity<>(oldEntry,HttpStatus.OK);
					}
			 }
			 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
