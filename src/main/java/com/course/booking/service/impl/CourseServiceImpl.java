package com.course.booking.service.impl;

import com.course.booking.common.response.Result;
import com.course.booking.controller.vo.CourseVO;
import com.course.booking.dao.CourseMapper;
import com.course.booking.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {



    @Resource
    private CourseMapper courseMapper;


    @Override
    public Result<List<CourseVO>> listCourse() {
        List<CourseVO> courseVOS = courseMapper.listCourse();
        return Result.success(courseVOS,courseVOS.size());
    }
}
