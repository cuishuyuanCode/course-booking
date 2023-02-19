package com.course.booking.controller;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.CourseDTO;
import com.course.booking.controller.vo.CourseVO;
import com.course.booking.service.CourseService;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/updateCourse")
    public Result<Boolean> updateCourse(@RequestBody CourseDTO courseDTO){
        return courseService.updateCourse(courseDTO);
    }

    @PostMapping("/addCourse")
    public Result<Boolean> addCourse(@RequestBody CourseDTO courseDTO){
        return courseService.addCourse(courseDTO);
    }


    @PostMapping("/deleteCourse")
    public Result<Boolean> deleteCourse(@RequestBody List<String> courseIds){
        return courseService.deleteCourse(courseIds);
    }
}
