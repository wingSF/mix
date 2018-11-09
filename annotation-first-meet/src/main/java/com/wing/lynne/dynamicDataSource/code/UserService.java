package com.wing.lynne.dynamicDataSource.code;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }
}
