package com.clipper.instagram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public List<User> findAll();
}
