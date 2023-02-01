package com.course.booking.controller;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.UserInfoVO;
import com.course.booking.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo(@RequestBody GetUserInfoDTO userInfoDTO){
        return Result.success(userService.getUserInfo(userInfoDTO));
    }

}
