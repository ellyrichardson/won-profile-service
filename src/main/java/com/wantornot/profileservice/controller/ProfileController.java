package com.wantornot.profileservice.controller;

import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wantornot.profileservice.dataaccesslayer.ProfileDAL;
import com.wantornot.profileservice.dataaccesslayer.ProfileRepository;
import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;
import com.wantornot.profileservice.domain.operations.UserProfileOperations;

@RestController
@RequestMapping(value = "/")
public class ProfileController {
	private final ProfileDAL profileDAL;
	
	public ProfileController(ProfileDAL profileDAL) {
		this.profileDAL = profileDAL;
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Object getProfileByUserId(@PathVariable String userId) {
		/*  
		 * TODO: 
		 * Add a checker if the logged in user was the one asking (SECURITY PURPOSES). 
		 * Example, an if statement that first checks for Token before 
		 * proceeding to getting the ownerID.
		 */
		return profileDAL.getProfileByUserId(userId);
	}
	
	@RequestMapping(value = "/{userId}/achievements", method = RequestMethod.GET)
	public Object getProfileAchievementsByUserId(@PathVariable String userId) {
		/*  
		 * TODO: 
		 * Add a checker if the logged in user was the one asking (SECURITY PURPOSES). 
		 * Example, an if statement that first checks for Token before 
		 * proceeding to getting the ownerID.
		 */
		
		// TODO: FIX THIS to have appropriate return
		return profileDAL.getAchievementsByUserId(userId);
	}
	
	@RequestMapping(value = "/{userId}/friends", method = RequestMethod.GET)
	public Object getProfileFriendsByUserId(@PathVariable String userId) {
		/*  
		 * TODO: 
		 * Add a checker if the logged in user was the one asking (SECURITY PURPOSES). 
		 * Example, an if statement that first checks for Token before 
		 * proceeding to getting the ownerID.
		 */
		
		return profileDAL.getAllFriends(userId);
	}
	
	/*
	@RequestMapping(value = "/{userId}/friends/{friendUserId}", method = RequestMethod.GET)
	public Object getSpecificProfileFriendByUserId(@PathVariable String userId, @PathVariable String friendUserId) {
		
		 * TODO: 
		 * Add a checker if the logged in user was the one asking (SECURITY PURPOSES). 
		 * Example, an if statement that first checks for Token before 
		 * proceeding to getting the ownerID.
		 *
		
		return profileDAL.getFriendProfileByUserId(friendUserId);
	}*/
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User createProfile(User user) {
		//LOG.info("Saving user.");
		return profileDAL.createProfile(user);
	}
	
	
	@RequestMapping(value = "/{userId}/update/{updateType}/{updateValue}", method = RequestMethod.POST)
	public User updateProfile(String userId, ProfileUpdateType profileUpdateType, Object profileUpdateValue) {
		//LOG.info("Saving user.");
		/*
		 * TODO: Fix this function to use the proper updating
		 * TODO: Update this function to have appropriate mechanism
		 * TODO: Add an exception catcher if User is null
		 * TODO: Update the user's dateUpdated date
		 */
		User user = profileDAL.getProfileByUserId(userId);
		UserProfileOperations userProfileOps = new UserProfileOperations();
		userProfileOps.updateProfileByType(user, profileUpdateType, profileUpdateValue);
		
		return profileDAL.updateProfile(user);
	}
	
	// NOTE: Only for testing
	public String deleteUser(String userId) {
		profileDAL.deleteProfile(userId);
		return userId;
	}
}
