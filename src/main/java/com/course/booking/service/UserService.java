package com.course.booking.service;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.TableDataInfo;
import com.course.booking.controller.vo.UserInfoVO;

public interface UserService {


    UserInfoVO getUserInfo();


    TableDataInfo listUser(UserInfoVO userInfoVO);
}
