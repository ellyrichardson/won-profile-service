package com.wantornot.profileservice.dataaccesslayer;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.Query;

import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;

public interface ProfileDAL {
	
	User getProfileByUserId(String userId);
	
	// Set<Achievement> getAllAchievement();

	Set<String> getAllFriends(String userId);
	
	Set<String> getAchievementsByUserId(String userId);
	
	//User getFriendProfileByUserId(String userId);

	User createProfile(User user);
	
	User updateProfile(User user);
	
	String deleteProfile(String userId);
	//User updateProfile(String userId, ProfileUpdateType profileUpdateType, String profileUpdateValue);
}
