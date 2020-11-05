package com.wantornot.profileservice.packager;

import java.util.HashSet;
import java.util.Set;

public class SetPackager {
	public static Set<String> packageThreeFriends() {
		Set<String> userSet = new HashSet<String>();
		userSet.add("userId1");
		userSet.add("userId2");
		userSet.add("userId3");
		return userSet;
	}
	
	public static Set<String> packageThreeAchievements() {
		Set<String> achievementSet = new HashSet<String>();
		achievementSet.add("achievementId1");
		achievementSet.add("achievementId2");
		achievementSet.add("achievementId3");
		return achievementSet;
	}
	
	public static Set<String> packageThreeWatchedWants() {
		Set<String> watchedWantsSet = new HashSet<String>();
		watchedWantsSet.add("wantId1");
		watchedWantsSet.add("wantId2");
		watchedWantsSet.add("wantId3");
		return watchedWantsSet;
	}
	
	public static Set<String> packageThreeSharedWants() {
		Set<String> sharedWantsSet = new HashSet<String>();
		sharedWantsSet.add("wantId1");
		sharedWantsSet.add("wantId2");
		sharedWantsSet.add("wantId3");
		return sharedWantsSet;
	}
}
