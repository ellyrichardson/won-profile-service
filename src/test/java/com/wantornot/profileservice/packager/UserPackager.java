package com.wantornot.profileservice.packager;

import java.time.ZonedDateTime;

import com.wantornot.profileservice.builder.UserBuilder;
import com.wantornot.profileservice.domain.User;

public class UserPackager {
	private static final ZonedDateTime BIRTHDATE = ZonedDateTime.parse("1998-10-03T10:15:30Z");
	private static final ZonedDateTime DATE_CREATED = ZonedDateTime.parse("2000-12-03T10:15:30Z");
	
	public static User packageBasicUser() {
		UserBuilder userBuilder = new UserBuilder(new User());
		return userBuilder
				.withFirstName("Elly")
				.withLastName("Richardson")
				.withId("id001")
				.withBirthDate(BIRTHDATE)
				.build();
	}
	
	public static User packageFullUser() {
		UserBuilder userBuilder = new UserBuilder(new User());
		return userBuilder
				.withFirstName("Elly")
				.withLastName("Richardson")
				.withId("id001")
				.withBirthDate(BIRTHDATE)
				.withDateCreated(DATE_CREATED)
				.withBadge("DUMMYBADGE")
				.withAchievements(SetPackager.packageThreeAchievements())
				.withFriends(SetPackager.packageThreeFriends())
				.withSharedWants(SetPackager.packageThreeSharedWants())
				.withWatchedWants(SetPackager.packageThreeWatchedWants())
				.build();
	}
}
