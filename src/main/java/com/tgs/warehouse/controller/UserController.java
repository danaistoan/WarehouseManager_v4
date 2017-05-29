package com.tgs.warehouse.controller;

import com.tgs.warehouse.entities.User;
import com.tgs.warehouse.services.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created by dana on 5/22/2017.
 */
@Controller
@RequestMapping("/Login")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        Objects.requireNonNull(userService);
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> authenticateUser(@RequestBody User user){
        try {
            User authenticatedUser = userService.processLogin(user);
            System.out.println("User authenticated with REST Controller");
            return new ResponseEntity<User>(authenticatedUser, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
