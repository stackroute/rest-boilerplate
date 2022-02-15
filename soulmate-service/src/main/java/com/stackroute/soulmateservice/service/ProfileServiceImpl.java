package com.stackroute.soulmateservice.service;

import com.stackroute.soulmateservice.exception.ProfileAlreadyExistsException;
import com.stackroute.soulmateservice.exception.ProfileNotFoundException;
import com.stackroute.soulmateservice.model.Profile;
import com.stackroute.soulmateservice.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMessageProducer profileMessageProducer;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile saveUser(Profile profile) throws ProfileAlreadyExistsException {
        log.debug("Inside saveUser()");
        Profile savedUser = new Profile();
        Optional<Profile> saveUser = profileRepository.findById(profile.getEmail());
            if (saveUser.isPresent())
            {
                log.error("Profile is already present");
                throw new ProfileAlreadyExistsException();
            }
            savedUser = profileRepository.save(profile);
        return savedUser;
    }

    @Override
    public List<Profile> getAllUsers() throws Exception {
        return (List<Profile>) profileRepository.findAll();
    }

    @Override
    public Profile deleteUser(String email) throws ProfileNotFoundException {
        log.debug("Inside deleteUser()");
        Profile profile = null;
        Optional optional = profileRepository.findById(email);
        try {
            if (optional.isPresent())
            {
                log.error("Profile is not present");
                throw new ProfileNotFoundException();
            }
            profile = profileRepository.findById(email).get();
            profileRepository.deleteById(email);
        }
        catch (Exception ex)
        {
            log.error("exception occur" + ex.getMessage());
        }
        return profile;
    }

    @Override
    public Profile updateUser(Profile profile) throws ProfileNotFoundException, ProfileAlreadyExistsException {
        log.debug("Inside updateUser()");
        Profile updatedProfile = null;
       Optional optional = profileRepository.findById(profile.getEmail());
       try {
           if (optional.isPresent()){
               log.error("Profile is not found");
               throw new ProfileNotFoundException();
           }
           Profile getProfile = profileRepository.findById(profile.getEmail()).get();
           getProfile.setEmail(profile.getEmail());
           updatedProfile = saveUser(getProfile);
       }
       catch (ProfileAlreadyExistsException e)
       {
           log.error("Profile is already present");
           throw new ProfileAlreadyExistsException();
       }
       catch (Exception e)
       {
           log.error("exception occur" + e.getMessage());
       }
        return updatedProfile;
    }

    @Override
    public Profile getUserByemail(String email) throws ProfileNotFoundException
    {
        Profile profile = null;
        try {
            profile = profileRepository.findById(email).orElse(null);
            if (profile!=null){
                return profile;
            }
            else {
                log.error("Profile is not found");
                throw new ProfileNotFoundException();
            }
        }
        catch (Exception ex)
        {
            log.error("exception occur" + ex.getMessage());
        }
        return profile;
    }
}
