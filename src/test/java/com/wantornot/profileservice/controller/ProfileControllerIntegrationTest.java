package com.wantornot.profileservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.wantornot.profileservice.TestConfig;
import com.wantornot.profileservice.builder.UserBuilder;
import com.wantornot.profileservice.dataaccesslayer.ProfileDALImpl;
import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;
import com.wantornot.profileservice.packager.SetPackager;
import com.wantornot.profileservice.packager.UserPackager;

import de.flapdoodle.embed.mongo.MongodExecutable;

@SpringBootTest
@Import(TestConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProfileControllerIntegrationTest {
	
	private static final ZonedDateTime BIRTHDATE = ZonedDateTime.parse("1998-10-03T10:15:30+01:00[Europe/Paris]");
	
	@Autowired
	private ProfileDALImpl testProfileDALImpl;
	
	@Autowired
	private MongoTemplate testMongoTemplate;
	
	@Autowired
	private MongodExecutable testMongodExecutable;
	
	private ProfileController target;
	
	@AfterEach
    public void clean() {
		testMongodExecutable.stop();
    }
	
	@BeforeEach
	public void setup() throws UnknownHostException, IOException {
		testMongodExecutable.start();
		testMongoTemplate.dropCollection("profile");
		target = new ProfileController(testProfileDALImpl);
	}
	
	@Test
	public void testGetProfileByUserId() {
		// Data Setup
		User testUser = UserPackager.packageBasicUser();
		target.createProfile(testUser);
		
		// Testing
		User retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		assertNotNull(retrievedUser);
		assertEquals(testUser.getId(), retrievedUser.getId());	
		
		// Cleaning up
		cleanUp(testUser.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProfileAchievementsByUserId() {
		// Data Setup
		User testUser = UserPackager.packageBasicUser();
		Set<String> testAchievements = SetPackager.packageThreeAchievements();
		testUser.setAchievements(testAchievements);
		target.createProfile(testUser);
		
		// Testing
		Set<String> retrievedAchievements = (Set<String>) target.getProfileAchievementsByUserId(testUser.getId());
		assertNotNull(retrievedAchievements);
		assertEquals(3, retrievedAchievements.size());
		
		// Cleaning up
		cleanUp(testUser.getId());	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProfileFriendsByUserId() {
		// Data Setup
		User testUser = UserPackager.packageBasicUser();
		Set<String> testFriends = SetPackager.packageThreeFriends();
		testUser.setFriends(testFriends);
		target.createProfile(testUser);
		
		// Testing
		Set<String> retrievedFriends = (Set<String>) target.getProfileFriendsByUserId(testUser.getId());
		assertNotNull(retrievedFriends);
		assertEquals(3, retrievedFriends.size());
		
		// Cleaning up
		cleanUp(testUser.getId());
	}
	
	/*
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSpecificProfileFriendByUserId() {
		// Data Setup
		User testUser = UserPackager.packageBasicUser();
		User testFriendUser = createAFriendUser(testUser);
		Set<String> testFriendsSet = new HashSet<String>();
		testFriendsSet.add(testFriendUser.getId());
		testUser.setFriends(testFriendsSet);
		target.createProfile(testUser);
		target.createProfile(testFriendUser);
		
		// Testing
		Set<String> retrievedFriends = (Set<String>) target.getSpecificProfileFriendByUserId(testUser.getId(), testFriendUser.getId());
		assertNotNull(retrievedFriends);
		assertEquals(1, retrievedFriends.size());
		assertEquals(testFriendUser.getId(), retrievedFriends.iterator().next());
		
		// Cleaning up
		cleanUp(testUser.getId());
		cleanUp(testFriendUser.getId());
	}*/
	
	// TODO: Test for DateUpdate attribute
	@Test
	public void testUpdateProfile() {
		Set<String> newAchievements = createAchievementSet();
		Set<String> newFriends = createFriendSet();
		Set<String> newSharedWants = createSharedWantSet();
		Set<String> newWatchedWants = createWatchedWantSet();
		String newBadge = "NewBADGE";
		String newFirstName = "newFIRSTNAME";
		String newLastName = "newLastNAME";
		ZonedDateTime newBIRTHDATE = ZonedDateTime.parse("2007-10-03T10:15:30Z");
		
		// Data Setup
		User testUser = UserPackager.packageFullUser();
		target.createProfile(testUser);
		
		// Testing
		target.updateProfile(testUser.getId(), ProfileUpdateType.ACHIEVEMENTS, newAchievements);
		User retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		testSetContents(newAchievements, retrievedUser.getAchievements());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.FRIENDS, newFriends);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		testSetContents(newFriends, retrievedUser.getFriends());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.SHAREDWANTS, newSharedWants);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		testSetContents(newSharedWants, retrievedUser.getSharedWants());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.WATCHEDWANTS, newWatchedWants);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		testSetContents(newWatchedWants, retrievedUser.getWatchedWants());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.BADGE, newBadge);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		assertEquals(newBadge, retrievedUser.getBadge());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.FIRSTNAME, newFirstName);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		assertEquals(newFirstName, retrievedUser.getFirstName());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.LASTNAME, newLastName);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		assertEquals(newLastName, retrievedUser.getLastName());
		
		target.updateProfile(testUser.getId(), ProfileUpdateType.BIRTHDATE, newBIRTHDATE);
		retrievedUser = (User) target.getProfileByUserId(testUser.getId());
		assertEquals(newBIRTHDATE, retrievedUser.getBirthDate());
		
		// Cleaning Up
		cleanUp(testUser.getId());
	}
	
	private void testSetContents(Set<String> expectedFriends, Set<String> actualFriends) {
		for (Iterator<String> it = expectedFriends.iterator(); it.hasNext(); ) {
	        String expected = it.next();
	        String actual = actualFriends.iterator().next();
	        assertEquals(expected, actual);
	    }
	}
	
	private void cleanUp(String testUserId) {
		// Cleaning up
		target.deleteUser(testUserId);
		User retrievedUser = (User) target.getProfileByUserId(testUserId);
		assertNull(retrievedUser);
	}
	
	// To create a friend to the tested user
	private User createAFriendUser(User friendUser) {
		Set<String> friendSet = new HashSet<String>();
		friendSet.add(friendUser.getId());
		
		UserBuilder userBuilder = new UserBuilder(new User());
		return userBuilder
				.withFirstName("Some")
				.withLastName("Other")
				.withId("id123")
				.withBirthDate(BIRTHDATE)
				.withFriends(friendSet)
				.build();
	}
	
	private Set<String> createAchievementSet() {
		Set<String> setAchievements = SetPackager.packageThreeAchievements();
		setAchievements.clear();
		setAchievements.add("newAchievementId");
		return setAchievements;
	}
	
	private Set<String> createFriendSet() {
		Set<String> setItem = SetPackager.packageThreeFriends();
		setItem.clear();
		setItem.add("newFriendId");
		return setItem;
	}
	
	private Set<String> createWatchedWantSet() {
		Set<String> setItem = SetPackager.packageThreeWatchedWants();
		setItem.clear();
		setItem.add("newWatchedWantId");
		return setItem;
	}
	
	private Set<String> createSharedWantSet() {
		Set<String> setItem = SetPackager.packageThreeSharedWants();
		setItem.clear();
		setItem.add("newSharedWantId");
		return setItem;
	}
}
