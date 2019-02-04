package com.reactive.project.service;

import com.reactive.project.domain.UserRegistrationDto;
import com.reactive.project.exception.UserNotFoundException;
import com.reactive.project.repository.UserRepository;
import com.reactive.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    List<User> findAll();

     void delete(User user);

     User save(User user);

    Optional<User> findById(Long id);
}
