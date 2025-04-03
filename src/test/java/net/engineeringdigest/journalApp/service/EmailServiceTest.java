package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

	@Autowired
	private EmailService emailService;
	
	@Test
	public void testSendMail() {
		emailService.sendEmail("chetangosavi81@gmail.com", "Testing Java Mail Sender", "Hie sending you this mail for testing my application");
	}
}
