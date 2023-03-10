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


    @Select("select count(*) = 1 from sys_user where user_name = #{loginDTO.username} and password = #{loginDTO.password}")
    Boolean loginCheck(@Param("loginDTO") LoginDTO loginDTO);

    @Insert("insert into sys_user(username,password) values (#{registerDTO.username},#{registerDTO.password})")
    void insertUser(@Param("registerDTO") RegisterDTO registerDTO);

    @Select("select count(1) >= 1 from sys_user where user_name = #{username}")
    Boolean selectUser(@Param("username") String username);
}
