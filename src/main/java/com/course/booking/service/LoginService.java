package com.course.booking.service;

import com.course.booking.common.response.Result;
import com.course.booking.controller.vo.CheckImageVO;

public interface LoginService {

    Result<CheckImageVO> getCheckImage();
}
