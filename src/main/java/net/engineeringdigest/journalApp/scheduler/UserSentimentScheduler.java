package net.engineeringdigest.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiments;
import net.engineeringdigest.journalApp.repositry.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;

@Component
public class UserSentimentScheduler {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	
	@Autowired
	private AppCache appCache;
	
	@Scheduled(cron = "0 9 * * * SUN")
	public void fetchUserAndSendMail() {
	List<User> users = userRepositoryImpl.getUserForSentimentAnalysis();	
	for(User user: users) {
		List<JournalEntry> entries =user.getJournalEntries();
		List<Sentiments> sentiments = entries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
		Map<Sentiments,Integer> sentimetnCountMap = new HashMap<>();
		
		for(Sentiments sentiment : sentiments) {
			if(sentiment != null) {
				sentimetnCountMap.put(sentiment, sentimetnCountMap.getOrDefault(sentiment, 0)+1);
			}
		}
		
		Sentiments mostFreqSentiment = null;
		int maxCount = 0;
		for(Map.Entry<Sentiments,Integer> entry: sentimetnCountMap.entrySet()) {
			if(entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				mostFreqSentiment = entry.getKey();
			}
		}
		if(mostFreqSentiment != null) {
			emailService.sendEmail(user.getEmail(), "Sentiment for Last 7 Days", "Sad/Negative Behaviour");
		}
		
	}
	
	}
	
	@Scheduled(cron = "0 */5 * ? * *")
	public void clearAppCache() {
		appCache.init();
	}
}
