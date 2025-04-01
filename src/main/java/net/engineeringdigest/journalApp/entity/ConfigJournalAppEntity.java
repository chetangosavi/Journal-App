package net.engineeringdigest.journalApp.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "ConfigJournalApp")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
	private String key;
	private String value;
}
