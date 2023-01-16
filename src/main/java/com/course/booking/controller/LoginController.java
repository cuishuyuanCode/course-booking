package com.course.booking.controller;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.LoginDTO;
import com.course.booking.controller.vo.CheckImageVO;
import com.course.booking.controller.vo.LoginVO;
import com.course.booking.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@CrossOrigin(originPatterns = "*")
public class LoginController {


    @Resource
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.GET, value = "/getCheckImage")
    public Result<CheckImageVO> getCheckImage(){
        return loginService.getCheckImage();
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        return loginService.login(loginDTO);
    }
}
