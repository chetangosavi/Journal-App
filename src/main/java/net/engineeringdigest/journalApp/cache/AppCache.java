package net.engineeringdigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repositry.ConfigJournalAppRepository;

@Component
public class AppCache {

	public enum keys{
		WEATHER_API;	
	}
	
	@Autowired
	private ConfigJournalAppRepository configJournalAppRepository;
	
	public Map<String, String> APP_CACHE;

	
	@PostConstruct
	public void init() {
		  APP_CACHE  = new HashMap<>();
		  List<ConfigJournalAppEntity> configList = configJournalAppRepository.findAll();
		  for(ConfigJournalAppEntity configJournalAppEntity : configList) {
			  APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
		  }
	}
}
