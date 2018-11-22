package com.wing.lynne.mapper;

import com.wing.lynne.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    int findOne();

    @Select("select count(1) from user")
    int findTwo();

    int insertUser(User user);

    @Select("select name from user where name like '%${name}%' ")
    String findUserByName(@Param("name") String name);

}
