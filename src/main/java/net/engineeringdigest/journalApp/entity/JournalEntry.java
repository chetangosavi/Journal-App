package net.engineeringdigest.journalApp.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

//import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="journal_entries")
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Getter
//@Setter
@Data
@NoArgsConstructor
public class JournalEntry {

	@Id 
	private String id;
	
	@NotBlank(message = "Title cannot be empty")
	private String title;
	private String description;
	private LocalDateTime date;
		
}
