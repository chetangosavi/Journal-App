package net.engineeringdigest.journalApp.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
		User findByUserName(String userName);
		void deleteByUserName(String userName);
}
