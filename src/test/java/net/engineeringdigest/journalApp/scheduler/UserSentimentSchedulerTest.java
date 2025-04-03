package net.engineeringdigest.journalApp.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSentimentSchedulerTest {
	
	@Autowired
	private UserSentimentScheduler userSentimentScheduler;
	
	@Test
	public void testFetchUserAndSendMail() {
		userSentimentScheduler.fetchUserAndSendMail();
	}

}
