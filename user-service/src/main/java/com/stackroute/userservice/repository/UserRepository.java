package com.stackroute.userservice.repository;

import com.stackroute.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Siva
 * @Date 10/30/2021 3:00 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
