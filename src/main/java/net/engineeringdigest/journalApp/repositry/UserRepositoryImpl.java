package net.engineeringdigest.journalApp.repositry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import net.engineeringdigest.journalApp.entity.User;

public class UserRepositoryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

	
	public List<User> getUserForSentimentAnalysis(){
		Query query = new Query();
		query.addCriteria(Criteria.where("email").regex(emailRegex));
		query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
		List<User> users = mongoTemplate.find(query, User.class);
		return users;
		
	}
}
