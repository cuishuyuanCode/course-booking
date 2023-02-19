package com.course.booking.dao;


import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {


    @Select("select * from sys_user where userName = #{getUserInfoDTO.username} and password = #{getUserInfoDTO.password}")
    UserInfoVO getUserInfo(GetUserInfoDTO getUserInfoDTO);

    @Select("select * from sys_user")
    List<UserInfoVO> listUser();

    @Select("select user_name from sys_user where user_id = #{userId}")
    UserInfoVO getUser(Integer userId);
}
