package com.course.booking.controller;

import com.course.booking.common.response.Result;
import com.course.booking.controller.vo.CourseVO;
import com.course.booking.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/course")
public class CourseController {


    @Resource
    private CourseService courseService;

    @GetMapping("/listCourse")
    public Result<List<CourseVO>> listCourse(){
        return courseService.listCourse();
    }
}
