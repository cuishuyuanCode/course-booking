package com.course.booking.service;


import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterResultVO;
import com.course.booking.controller.vo.RouterVO;

import java.util.List;

public interface RouterService {


    List<RouterResultVO> getRouters();
}
