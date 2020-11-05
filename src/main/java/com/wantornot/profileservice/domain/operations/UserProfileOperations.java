package com.wantornot.profileservice.domain.operations;

import java.time.ZonedDateTime;
import java.util.Set;

import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;

public class UserProfileOperations {
	@SuppressWarnings("unchecked")
	public void updateProfileByType(User userProfile, ProfileUpdateType updateType, Object updateValue) {
		switch(updateType) {
		case ACHIEVEMENTS:
			// TODO: Work on this
			userProfile.setAchievements((Set<String>) updateValue);
			break;
		case BIRTHDATE:
			userProfile.setBirthDate((ZonedDateTime) updateValue);
			break;
		case FIRSTNAME:
			userProfile.setFirstName((String) updateValue);
			break;
		case LASTNAME:
			userProfile.setLastName((String) updateValue);
			break;
		case FRIENDS:
			userProfile.setFriends((Set<String>) updateValue);
			break;
		case SHAREDWANTS:
			userProfile.setSharedWants((Set<String>) updateValue);
			break;
		case WATCHEDWANTS:
			userProfile.setWatchedWants((Set<String>) updateValue);
			break;
		default:
			userProfile.setBadge((String) updateValue);
		}
	}
}
