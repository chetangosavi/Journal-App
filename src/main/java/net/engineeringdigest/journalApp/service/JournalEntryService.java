package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repositry.JournalEntryRepository;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository jERepo;
	
	@Autowired
	private UserService userService;
	
	@Transactional  
	public void saveEntry(JournalEntry journalEntry , String username) {
		
		try {
			User user = userService.findByUserName(username);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry savedEntry = jERepo.save(journalEntry);
			user.getJournalEntries().add(savedEntry);
			userService.saveUser(user);
		} catch (Exception e) {
			throw new RuntimeException("An error occured while saving the entry");
		}
		
	}
	
	public void saveEntry(JournalEntry journalEntry ) {
		journalEntry.setDate(LocalDateTime.now());
		jERepo.save(journalEntry);
	}
	
	public List<JournalEntry> getAll(){
		return jERepo.findAll();
	}
	
	public Optional<JournalEntry> getById(String id) {
		return jERepo.findById(id); 
	}
	
	@Transactional
	public void deleteById(String id, String username){
		User user = userService.findByUserName(username);
		user.getJournalEntries().removeIf(x-> x.getId().equals(id));
		userService.saveUser(user);
		jERepo.deleteById(id);
	}

	
}
