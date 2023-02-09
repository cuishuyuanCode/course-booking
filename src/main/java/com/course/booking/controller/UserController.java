package com.course.booking.controller;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.TableDataInfo;
import com.course.booking.controller.vo.UserInfoVO;
import com.course.booking.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo(){
        return Result.success(userService.getUserInfo());
    }

    @GetMapping("/list")
    public TableDataInfo listUser(){
        return userService.listUser();
    }

}
