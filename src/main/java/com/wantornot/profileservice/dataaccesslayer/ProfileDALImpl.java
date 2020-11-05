package com.wantornot.profileservice.dataaccesslayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wantornot.profileservice.domain.User;
import com.wantornot.profileservice.domain.User.ProfileUpdateType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProfileDALImpl implements ProfileDAL {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User getProfileByUserId(String userId) {
		return mongoTemplate.findById(userId, User.class, "user");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getAllFriends(String userId) {
		//query.fields().include("friends");
		//List<String> friendsList = mongoTemplate.find(query, User.class).stream().map(User::getFriends));
		//return new HashSet<>(friendsList);
		//return 
		User result = mongoTemplate.findById(userId, User.class, "user");//.stream().map(User::getFriends));
		return result.getFriends();
	}

	// NOTE: This function is pointless (CONSIDER REMOVING)
	/*
	@Override
	public User getFriendProfileByUserId(String userId) {
		// TODO Make sure the user and the friend have mutual "Friend" state for each other
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));
		return mongoTemplate.findById(query, User.class);
	}*/

	@Override
	public User createProfile(User user) {
		saveUserToDB(user);
		return user;
	}

	@Override
	public User updateProfile(User user) {
		saveUserToDB(user);
		return user;
	}
	
	@Override
	public String deleteProfile(String userId) {
		mongoTemplate.remove(getProfileByUserId(userId));
		return userId;
	}
	
	private void saveUserToDB(User user) {
		mongoTemplate.save(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getAchievementsByUserId(String userId) {
		/*
		Query query = new Query();
		query.fields().include("achievements");
		query.addCriteria(Criteria.where("id").is(userId));
		List<String> achievementsList = (List<String>) mongoTemplate.findById(query, User.class);*/
		User result = mongoTemplate.findById(userId, User.class, "user");
		return result.getAchievements();
	}

}
