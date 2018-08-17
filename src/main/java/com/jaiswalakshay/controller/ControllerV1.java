package com.jaiswalakshay.controller;

import com.jaiswalakshay.model.User;
import com.jaiswalakshay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/v1/user")
@Transactional
public class ControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username,@RequestHeader(value = "X-TENANT-ID",required = true) String tenantId) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user,@RequestHeader(value = "X-TENANT-ID",required = true)String tenantId) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("{\"status\":\"UP\"}", HttpStatus.OK);
    }

}