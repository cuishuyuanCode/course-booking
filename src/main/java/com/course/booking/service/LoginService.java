package com.course.booking.service;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.LoginDTO;
import com.course.booking.controller.vo.CheckImageVO;
import com.course.booking.controller.vo.LoginVO;

public interface LoginService {

    Result<CheckImageVO> getCheckImage();

    Result<LoginVO> login(LoginDTO loginDTO);
}
