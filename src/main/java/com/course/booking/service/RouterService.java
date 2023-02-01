package com.course.booking.service;


import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterVO;

public interface RouterService {


    RouterVO getRouters(GetUserInfoDTO userInfo);
}
