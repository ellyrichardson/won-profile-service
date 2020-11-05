package com.wantornot.profileservice.builder;

import java.time.ZonedDateTime;
import java.util.Set;

import com.wantornot.profileservice.domain.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserBuilder {
	private User user;
	
	public UserBuilder withId(String id) {
		user.setId(id);
		return this;
	}
	
	public UserBuilder withFirstName(String firstName) {
		user.setFirstName(firstName);
		return this;
	}
	
	public UserBuilder withLastName(String lastName) {
		user.setLastName(lastName);
		return this;
	}
	
	public UserBuilder withDateCreated(ZonedDateTime dateCreated) {
		user.setDateCreated(dateCreated);
		return this;
	}
	
	public UserBuilder withDateUpdated(ZonedDateTime dateUpdated) {
		user.setDateUpdated(dateUpdated);
		return this;
	}
	
	public UserBuilder withAchievements(Set<String> achievements) {
		user.setAchievements(achievements);
		return this;
	}
	
	public UserBuilder withFriends(Set<String> friends) {
		user.setFriends(friends);
		return this;
	}
	
	public UserBuilder withWatchedWants(Set<String> watchedWants) {
		user.setWatchedWants(watchedWants);
		return this;
	}
	
	public UserBuilder withSharedWants(Set<String> sharedWants) {
		user.setSharedWants(sharedWants);
		return this;
	}
	
	public UserBuilder withBirthDate(ZonedDateTime birthDate) {
		user.setBirthDate(birthDate);
		return this;
	}
	
	public UserBuilder withBadge(String badge) {
		user.setBadge(badge);
		return this;
	}
	
	public User build() {
		return this.user;
	}
}
