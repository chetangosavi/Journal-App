package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("USER")
@Data
@NoArgsConstructor
public class User {
	
	@Id
	private String id;
	
	/*
	spring boot wont create auto indexing we will have to 
	manually mention it in spring application.properties
	spring.data.mongodb.auto-index-creation=true
	*/
	@Indexed(unique = true)
	@NonNull
	private String userName;
	@NonNull
	private String password;
	
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();
	private List<String> role;
	
}
