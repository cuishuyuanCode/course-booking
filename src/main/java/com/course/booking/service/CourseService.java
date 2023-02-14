package com.course.booking.service;

import com.course.booking.common.response.Result;
import com.course.booking.controller.vo.CourseVO;

import java.util.List;

public interface CourseService {


    Result<List<CourseVO>> listCourse();
}
