package com.wantornot.profileservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.wantornot.profileservice.builder.UserBuilder;
import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;
import com.wantornot.profileservice.domain.operations.UserProfileOperations;
import com.wantornot.profileservice.packager.SetPackager;

/*
 * TODO: Test for DATE_UPDATE attribute of the user
 * */
public class UserProfileOperationsTest {
	private static final String FNAME = "fName";
	private static final String LNAME = "lName";
	private static final String BADGE = "badgeName";
	private static final ZonedDateTime DATE_CREATED = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
	private static final ZonedDateTime BIRTHDATE = ZonedDateTime.parse("1998-10-03T10:15:30+01:00[Europe/Paris]");
	private static final String NEW_FNAME = "newFirstName";
	private static final String NEW_LNAME = "newLastName";
	private static final String NEW_BADGE = "newBadgeName";
	private static final ZonedDateTime NEW_BIRTHDATE = ZonedDateTime.parse("1998-10-03T10:15:30+01:00[Europe/Paris]");
	
	
	@Test
	public void testUpdateProfileByUpdateType() {
		User mockUser = createNewUser();
		testInitialProfileValues(mockUser);
		updateProfileValues(mockUser);
		testNewProfileValues(mockUser);
	}
	
	private void testInitialProfileValues(User mockUser) {
		assertEquals(FNAME, mockUser.getFirstName());
		assertEquals(LNAME, mockUser.getLastName());
		assertEquals(BADGE, mockUser.getBadge());
		assertEquals(SetPackager.packageThreeFriends(), mockUser.getFriends());
		assertEquals(SetPackager.packageThreeAchievements(), mockUser.getAchievements());
		assertEquals(SetPackager.packageThreeSharedWants(), mockUser.getSharedWants());
		assertEquals(SetPackager.packageThreeWatchedWants(), mockUser.getWatchedWants());
		assertEquals(DATE_CREATED, mockUser.getDateCreated());
		assertEquals(BIRTHDATE, mockUser.getBirthDate());
	}
	
	private void testNewProfileValues(User mockUser) {
		assertEquals(NEW_FNAME, mockUser.getFirstName());
		assertEquals(NEW_LNAME, mockUser.getLastName());
		assertEquals(NEW_BADGE, mockUser.getBadge());
		assertEquals(createFriendSet(), mockUser.getFriends());
		assertEquals(createAchievementSet(), mockUser.getAchievements());
		assertEquals(createSharedWantsSet(), mockUser.getSharedWants());
		assertEquals(createWatchedWantsSet(), mockUser.getWatchedWants());
		assertEquals(DATE_CREATED, mockUser.getDateCreated());
		assertEquals(NEW_BIRTHDATE, mockUser.getBirthDate());
	}
	
	private void updateProfileValues(User mockUser) {
		UserProfileOperations target = new UserProfileOperations();
		target.updateProfileByType(mockUser, ProfileUpdateType.FIRSTNAME, NEW_FNAME);
		target.updateProfileByType(mockUser, ProfileUpdateType.LASTNAME, NEW_LNAME);
		target.updateProfileByType(mockUser, ProfileUpdateType.BADGE, NEW_BADGE);
		target.updateProfileByType(mockUser, ProfileUpdateType.FRIENDS, createFriendSet());
		target.updateProfileByType(mockUser, ProfileUpdateType.ACHIEVEMENTS, createAchievementSet());
		target.updateProfileByType(mockUser, ProfileUpdateType.SHAREDWANTS, createSharedWantsSet());
		target.updateProfileByType(mockUser, ProfileUpdateType.WATCHEDWANTS, createWatchedWantsSet());
		target.updateProfileByType(mockUser, ProfileUpdateType.BIRTHDATE, NEW_BIRTHDATE);
	}
	
	private User createNewUser() {
		UserBuilder userBuilder = new UserBuilder(new User());
		return userBuilder.withFirstName(FNAME)
				.withLastName(LNAME)
				.withBadge(BADGE)
				.withFriends(SetPackager.packageThreeFriends())
				.withAchievements(SetPackager.packageThreeAchievements())
				.withSharedWants(SetPackager.packageThreeSharedWants())
				.withWatchedWants(SetPackager.packageThreeWatchedWants())
				.withDateCreated(DATE_CREATED)
				.withBirthDate(BIRTHDATE)
				.build();
	}
	
	private Set<String> createFriendSet() {
		Set<String> friends = SetPackager.packageThreeFriends();
		friends.add("newFriend");
		return friends;
	}
	
	private Set<String> createAchievementSet() {
		Set<String> achievements = SetPackager.packageThreeAchievements();
		achievements.add("newAchievement");
		return achievements;
	}
	
	private Set<String> createSharedWantsSet() {
		Set<String> sharedWants = SetPackager.packageThreeSharedWants();
		sharedWants.add("newSharedWants");
		return sharedWants;
	}
	
	private Set<String> createWatchedWantsSet() {
		Set<String> watchedWants = SetPackager.packageThreeWatchedWants();
		watchedWants.add("newWatchedWants");
		return watchedWants;
	}
}
