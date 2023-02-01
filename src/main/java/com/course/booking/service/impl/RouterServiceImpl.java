package com.course.booking.service.impl;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterVO;
import com.course.booking.dao.RouterMapper;
import com.course.booking.service.RouterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RouterServiceImpl  implements RouterService {


    @Resource
    private RouterMapper routerMapper;

    @Override
    public RouterVO getRouters(GetUserInfoDTO userInfo) {
        return routerMapper.getRouters(userInfo);
    }
}
