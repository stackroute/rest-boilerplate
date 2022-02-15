package com.stackroute.soulmateservice.service;

import com.stackroute.soulmateservice.exception.ProfileAlreadyExistsException;
import com.stackroute.soulmateservice.exception.ProfileNotFoundException;
import com.stackroute.soulmateservice.model.Profile;

import java.util.List;

public interface ProfileService {
    Profile saveUser(Profile profile) throws ProfileAlreadyExistsException;
    List<Profile> getAllUsers() throws Exception;
    Profile deleteUser(String email) throws ProfileNotFoundException;
    Profile updateUser(Profile profile) throws ProfileNotFoundException, ProfileAlreadyExistsException;
    Profile getUserByemail(String email) throws ProfileNotFoundException;
}
