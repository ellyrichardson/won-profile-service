package com.wantornot.profileservice.domain;

import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	public enum ProfileUpdateType {
		FIRSTNAME,
		LASTNAME,
		ACHIEVEMENTS,
		FRIENDS,
		SHAREDWANTS,
		BIRTHDATE,
		WATCHEDWANTS,
		BADGE
	}
	
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private ZonedDateTime dateCreated;
	private ZonedDateTime dateUpdated;
	private Set<String> achievements;
	private Set<String> friends;
	private Set<String> sharedWants;
	private ZonedDateTime birthDate;
	private Set<String> watchedWants;
	private String badge;
}	
