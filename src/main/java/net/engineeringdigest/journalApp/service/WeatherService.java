package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String API_KEY;

	@Autowired
	private AppCache appCache;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWether(String city) {
		 String finalAPI= appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, "Mumbai").replace(Placeholders.API_KEY, API_KEY);
		  ResponseEntity<WeatherResponse> response  = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class );
		WeatherResponse body = response.getBody();
		return body;
		
	}
	

}
