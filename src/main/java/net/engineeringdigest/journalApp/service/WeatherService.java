package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String API_KEY;
	private static final String API =  "https://api.openweathermap.org/data/2.5/weather?q=CITY&units=metric&appid=API_KEY";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWether(String city) {
		 String finalAPI= API.replace("CITY", "Mumbai").replace("API_KEY", API_KEY);
		  ResponseEntity<WeatherResponse> response  = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class );
		WeatherResponse body = response.getBody();
		return body;
		
	}
	

}
