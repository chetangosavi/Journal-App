package net.engineeringdigest.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.engineeringdigest.journalApp.repositry.UserRepositoryImpl;

@SpringBootTest
public class UserRepositoryImplTests {

	@Autowired
	UserRepositoryImpl userRepositoryImpl;
	
	@Test
	public void getUserForSentimentAnalysis() {
		userRepositoryImpl.getUserForSentimentAnalysis();
	}
}
