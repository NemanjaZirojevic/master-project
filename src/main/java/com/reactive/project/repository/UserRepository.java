package com.reactive.project.repository;

import com.reactive.project.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

 User findByEmail(String email);

 List<User> findAll();

 User save(User user);

 void delete(User user);

 Optional<User> findById(Long id);
}
