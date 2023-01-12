package com.course.booking.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    @Select("select 1 from teacher")
    String selectDemo();

}
