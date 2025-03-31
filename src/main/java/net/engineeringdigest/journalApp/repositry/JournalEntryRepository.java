package net.engineeringdigest.journalApp.repositry;

//import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalApp.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry ,String>{
	
}
