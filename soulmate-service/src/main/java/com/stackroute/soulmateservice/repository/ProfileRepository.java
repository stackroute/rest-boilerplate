package com.stackroute.soulmateservice.repository;

import com.stackroute.soulmateservice.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
}
