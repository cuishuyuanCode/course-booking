package com.course.booking.dao;

import com.course.booking.controller.vo.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {


    @Select("select * from course")
    List<CourseVO> listCourse();
}
