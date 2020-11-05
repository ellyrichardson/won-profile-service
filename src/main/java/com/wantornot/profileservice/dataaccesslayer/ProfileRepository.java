package com.wantornot.profileservice.dataaccesslayer;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wantornot.profileservice.domain.User;

public interface ProfileRepository extends MongoRepository<User, String> {

}
