package com.course.booking.controller;


import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterResultVO;
import com.course.booking.controller.vo.RouterVO;
import com.course.booking.service.RouterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RouterController {


    @Resource
    private RouterService routerService;

    @GetMapping("/getRouters")
    public Result<List<RouterResultVO>> getRouters(){
        return Result.success(routerService.getRouters());
    }
}
