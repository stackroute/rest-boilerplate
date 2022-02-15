package com.stackroute.userservice.service;

import com.stackroute.userservice.model.User;

import java.util.Map;

/**
 * @Author Siva
 * @Date 10/30/2021 2:59 PM
 */
public interface JwtTokenGenerator {
    Map<String,String> generateToken(User user);
}
