package com.stackroute.userservice.controller;

import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.JwtTokenGenerator;
import com.stackroute.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Siva
 * @Date 10/30/2021 3:01 PM
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {
    private UserService userService;
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public UserController(UserService userService, JwtTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/loginuser")
    public ResponseEntity<?> authenticateUser (@RequestBody User user){
        log.debug("Login request received for user" + user + "at " + java.time.LocalDateTime.now());
        ResponseEntity responseEntity = null;
        Map<String, String> tokenMap = null;
        try {
            boolean result = userService.validateUser(user);
            if (result){
                tokenMap=jwtTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            log.error("Exception occur" + e.getMessage());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
