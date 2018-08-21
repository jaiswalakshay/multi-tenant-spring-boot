package com.jaiswalakshay.services;

import com.jaiswalakshay.annotation.CrossTenancyGet;
import com.jaiswalakshay.model.User;
import com.jaiswalakshay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @CrossTenancyGet
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @CrossTenancyGet
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
