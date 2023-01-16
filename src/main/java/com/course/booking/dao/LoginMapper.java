package com.course.booking.dao;

import com.course.booking.controller.dto.LoginDTO;
import com.course.booking.controller.dto.RegisterDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    @Select("select 1 from teacher")
    String selectDemo();


    @Select("select count(*) = 1 from user where username = #{loginDTO.username}")
    Boolean loginCheck(@Param("loginDTO") LoginDTO loginDTO);

    @Insert("insert into user(username,password) values (#{registerDTO.username},#{registerDTO.password})")
    void insertUser(@Param("registerDTO") RegisterDTO registerDTO);

    @Select("select count(1) >= 1 from user where username = #{username}")
    Boolean selectUser(@Param("username") String username);
}
