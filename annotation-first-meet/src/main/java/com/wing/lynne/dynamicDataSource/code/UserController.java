package com.wing.lynne.dynamicDataSource.code;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }
}
