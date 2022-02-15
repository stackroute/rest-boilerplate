package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author Siva
 * @Date 10/30/2021 2:59 PM
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        log.debug("inside saveUser()");
        User savedUser = null;
        Optional<User> saveUser = userRepository.findById(user.getEmail());
        try {
            if (saveUser.isPresent())
            {
                log.error("User is already present");
                throw new UserAlreadyExistsException("User with this email is already registered");
            }
            savedUser = userRepository.save(user);
        }catch (Exception ex)
        {
            log.error("exception occur" + ex.getMessage());
        }
        return savedUser;
    }

    @Override
    public User findUserByEmailId(String email) throws UserNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(email).orElse(null);
            if (user!=null){
                return user;
            }
            else {
                throw new UserNotFoundException("User is not present");
            }
        }
        catch (Exception ex)
        {
            log.error("exception occur" + ex.getMessage());
        }
        return user;
    }

    @Override
    public boolean validateUser(User user) throws UserNotFoundException {
        User retriveUser = findUserByEmailId(user.getEmail());
        if (user.getPassword().equals(retriveUser.getPassword()))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
