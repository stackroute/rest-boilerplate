package com.stackroute.soulmateservice.controller;

import com.stackroute.soulmateservice.exception.ProfileAlreadyExistsException;
import com.stackroute.soulmateservice.exception.ProfileNotFoundException;
import com.stackroute.soulmateservice.model.Profile;
import com.stackroute.soulmateservice.model.User;
import com.stackroute.soulmateservice.service.ProfileMessageProducer;
import com.stackroute.soulmateservice.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProfileController {
    private ProfileService profileService;
    private ResponseEntity responseEntity;
    private ProfileMessageProducer profileMessageProducer;

    @Autowired
    public ProfileController(ProfileService profileService, ProfileMessageProducer profileMessageProducer) {
        this.profileService = profileService;
        this.profileMessageProducer = profileMessageProducer;
    }

    @PostMapping("/user")
    public ResponseEntity<Profile> saveUser(@Valid @RequestBody Profile profile) throws ProfileAlreadyExistsException {
        log.debug("Save request received for profile" + profile + "at " + java.time.LocalDateTime.now());
        try {
            log.info("profile details" + profile);
            Profile savedProfile = profileService.saveUser(profile);
            log.info("Saved user successfully" + savedProfile);
            User user = new User();
            user.setEmail(profile.getEmail());
            user.setPassword(profile.getPassword());
            profileMessageProducer.sendMessageToRabbitMq(user);
            responseEntity = new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        }
        catch (ProfileAlreadyExistsException e)
        {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    @GetMapping("/users")
    public ResponseEntity<List<Profile>> getAllUsers() throws Exception {
        responseEntity = new ResponseEntity<List<Profile>>((List<Profile>) profileService.getAllUsers(),HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Profile> deleteUser(@PathVariable("email") String email) throws ProfileNotFoundException {

        try {
            responseEntity = new ResponseEntity<>(profileService.deleteUser(email),HttpStatus.OK);
        }
        catch (ProfileNotFoundException e)
        {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("user")
    public ResponseEntity<Profile> updateUser(@RequestBody Profile profile) throws ProfileNotFoundException, ProfileAlreadyExistsException {
        log.debug("Update request received for profile" + profile + "at " + java.time.LocalDateTime.now());
        try {
            Profile updatedProfile = profileService.updateUser(profile);
            if (profileService.updateUser(profile) != null) {
                responseEntity = new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (ProfileNotFoundException e)
        {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<Profile> getUserByEmail(@PathVariable("email") String email) throws ProfileNotFoundException {
        responseEntity = new ResponseEntity<>(profileService.getUserByemail(email),HttpStatus.OK);
        return responseEntity;
    }
}
