package com.course.booking.service.impl;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.UserInfoVO;
import com.course.booking.dao.UserMapper;
import com.course.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public UserInfoVO getUserInfo(GetUserInfoDTO getUserInfoDTO) {
        log.info("开始查询用户信息");
        return userMapper.getUserInfo(getUserInfoDTO);
    }
}
