package com.wing.lynne.mapper;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    int findOne();

    @Select("select count(1) from user")
    int findTwo();
}
