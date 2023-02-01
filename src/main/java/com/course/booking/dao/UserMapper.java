package com.course.booking.dao;


import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    @Select("select * from sys_user where userName = #{getUserInfoDTO.username} and password = #{getUserInfoDTO.password}")
    UserInfoVO getUserInfo(GetUserInfoDTO getUserInfoDTO);
}
